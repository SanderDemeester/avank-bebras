/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.ClassPupil;
import models.dbentities.UserModel;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.Role;
import models.user.Teacher;
import models.user.User;
import models.user.UserType;
import models.util.IDWrapper;
import models.util.OperationResultInfo;
import play.data.Form;
import play.mvc.Result;
import views.html.classes.addExistingPupil;
import views.html.classes.classpupilManagement;
import views.html.classes.oldClassPupilManagement;
import views.html.commons.noaccess;
import controllers.EController;
import controllers.classgroups.ClassPupilManager.DataSet;

/**
 * @author Jens N. Rammant
 * TODO split in multiple classes, confirmation screen
 */
public class ClassPupilController extends EController {
	
	/**
	 * Returns the page with info and students (only current if class is active, otherwise all)
	 * @param id id of the class
	 * @param page which page of students
	 * @param orderBy what to order students by
	 * @param order how to order
	 * @param filter what to filter on
	 * @return page with info and students
	 */
	public static Result viewClass(int id,int page, String orderBy, String order, String filter){
		//Setting up template arguments
		List<Link> breadcrumbs = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();		
		
		//Fetch the class
		ClassGroup cg = null;
		try{
			cg = Ebean.find(ClassGroup.class).where().eq("id", id).findUnique();
		}catch(PersistenceException pe) {
			cg = null;
		}
		//Determine which subset of students to be shown on the main page (active-->only active, not active --> all)
		DataSet ds = ClassPupilManager.DataSet.ALL;
		if(cg!=null && cg.isActive())ds=ClassPupilManager.DataSet.ACTIVE;
		//Configure the manager
		ClassPupilManager cpm = new ClassPupilManager(id, ds, ModelState.READ);
		cpm.setFilter(filter);
		cpm.setOrder(order);
		cpm.setOrderBy(orderBy);
		//Allow removal of students is class is active
		if(cg!=null && cg.isActive())cpm.setCanRemove(true);
		//Try to render the list
		try{
			//Check if authorized
			if(!isAuthorized(id))return ok(noaccess.render(breadcrumbs));
			@SuppressWarnings("unused")
			int temp = cg.id; //This will throw a NullPointerException if cg == null, and will then go to the catch
			return ok(
				classpupilManagement.render(cpm.page(page),cpm,orderBy,order,filter,breadcrumbs,ori,cg)
				);
		}catch(Exception pe){
			//Show page with error
			ori.add(EMessages.get("classes.pupil.error.classfetch"),OperationResultInfo.Type.ERROR);
			return ok(
				classpupilManagement.render(null,cpm,orderBy,order,filter,breadcrumbs,ori,null)
			);
		}
		
	}

	/**
	 * Returns the page of students that used to be in this class
	 * @param id id of the class
	 * @param page page of students to show
	 * @param orderBy what to order on
	 * @param order how to order
	 * @param filter what to filer on
	 * @return page with old pupils
	 */
	public static Result viewOldPupils(int id,int page, String orderBy, String order, String filter){
		//Setting up template arguments
		List<Link> breadcrumbs = getBreadCrumbs(id);
		breadcrumbs.add(new Link(EMessages.get("classes.pupil.oldpupillist"),"/classes/"+id+"/old"));
		OperationResultInfo ori = new OperationResultInfo();		
		
		//Configure manager
		ClassPupilManager cpm = new ClassPupilManager(id, ClassPupilManager.DataSet.NOTACTIVE,
				ModelState.READ);
		cpm.setFilter(filter);
		cpm.setOrder(order);
		cpm.setOrderBy(orderBy);
		//Try to render the list
		try{
			//Check if authorized
			if(!isAuthorized(id))return ok(noaccess.render(breadcrumbs));
			return ok(
				oldClassPupilManagement.render(cpm.page(page),cpm,orderBy,order,filter,breadcrumbs,ori)
				);
		}catch(Exception pe){
			//Show empty page with error
			ori.add(EMessages.get("classes.pupil.error.classfetch"),OperationResultInfo.Type.ERROR);
			return ok(
				oldClassPupilManagement.render(null,cpm,orderBy,order,filter,breadcrumbs,ori)
			);
		}
	}
	
	public static Result removeStudent(int classID,String pupilID){		
		//Setting up template arguments
		List<Link> breadcrumbs = getBreadCrumbs(classID);
		OperationResultInfo ori = new OperationResultInfo();		
		
		//Do the actual deleting from the class. Needs to be in a transaction
		Ebean.beginTransaction();
		try{
			//Check if authorized
			if(!isAuthorized(classID))return ok(noaccess.render(breadcrumbs));
			remove(classID,pupilID);
			flash("deletesuccess","");
			Ebean.commitTransaction();
		}catch(PersistenceException pe){
			flash("deleteerror","");
			Ebean.rollbackTransaction();
		} finally {
			Ebean.endTransaction();
		}
		//Return to the list page
		return redirect(routes.ClassPupilController.viewClass(classID, 0,"id", "asc", ""));
	}
	
	/**
	 * 
	 * @param id of the classgroup
	 * @return a form to add an existing student to the classgroup
	 */
	public static Result addExistingStudent(int id){
		List<Link> bc = getBreadCrumbs(id);
		bc.add(new Link(EMessages.get("classes.pupil.add"),"/classes/"+id+"/add"));
		OperationResultInfo ori = new OperationResultInfo();
		try{
			if(!isAuthorized(id))return ok(noaccess.render(bc));
		}catch(PersistenceException pe){
			ori.add(EMessages.get("classes.pupil.error.classfetch"),OperationResultInfo.Type.ERROR);
			return ok(addExistingPupil.render(null, bc, ori, id));
		}
		Form<IDWrapper> f = new Form<IDWrapper>(IDWrapper.class);
		return ok(
				addExistingPupil.render(f, bc, ori, id));
	}

	/**
	 * Saves the linking of the existing student to the classgroup
	 * 
	 * @param id
	 *            of the classgroup
	 * @return the page with the list og students
	 */
	public static Result saveExisting(int id) {
		// Initialize template arguments
		List<Link> bc = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();
		UserModel um = null;
		// Retrieve form
		Form<IDWrapper> f = form(IDWrapper.class).bindFromRequest();
		try {
			// Check if authorized
			if (!isAuthorized(id))
				return ok(noaccess.render(bc));		
			
			if (f.hasErrors()) {
				// If incomplete, show form with warning
				ori.add(EMessages.get("classes.pupil.add.incomplete"),
						OperationResultInfo.Type.WARNING);
				return badRequest(addExistingPupil.render(f, bc, ori, id));
			}
			// Retrieve id
			IDWrapper i = f.get();			
			// Retrieve usermodel of to be linked pupil
			um = Ebean.find(UserModel.class, i.id);
		} catch (PersistenceException pe) {
			// Retrieval failed, Show form with error
			ori.add(EMessages.get("classes.pupil.add.error"),
					OperationResultInfo.Type.ERROR);
			return badRequest(addExistingPupil.render(f, bc, ori, id));
		}
		if (um == null) {
			// No user with that id exists
			ori.add(EMessages.get("classes.pupil.add.usernotexist"),
					OperationResultInfo.Type.WARNING);
			return badRequest(addExistingPupil.render(f, bc, ori, id));
		}
		if (um.type != UserType.INDEPENDENT && um.type != UserType.PUPIL) { //TODO check if both are needed
			// User is not a pupil
			ori.add(EMessages.get("classes.pupil.add.usernotpupil"),
					OperationResultInfo.Type.WARNING);
			return badRequest(addExistingPupil.render(f, bc, ori, id));
		}
		// Check if pupil is already in the class
		if ( um.classgroup!=null && um.classgroup == id) {
			ori.add(EMessages
				.get("classes.pupil.add.useralreadyinclass"),
				OperationResultInfo.Type.WARNING);
				return badRequest(addExistingPupil.render(f, bc, ori, id));
		}
		//Actually save the linking
		Ebean.beginTransaction();
		try {
			add(id, um.id);
			Ebean.commitTransaction();			
		} catch (PersistenceException pe) {
			// Saving/retrieval failed, show error
			Ebean.rollbackTransaction();
			ori.add(EMessages.get("classes.user.add.error"),
					OperationResultInfo.Type.ERROR);
			return badRequest(addExistingPupil.render(f, bc, ori, id));
		}finally{
			Ebean.endTransaction();
		}
		// redirect to list page
		return redirect(routes.ClassPupilController.viewClass(id, 0, "id", "asc", ""));
	}
	
	/**
	 * 
	 * @param id the id of the class
	 * @return whether the current user is authorized to view/edit this class
	 * @throws PersistenceException when something goes wrong with the db
	 */
	protected static boolean isAuthorized(int id) throws PersistenceException{
		if(!AuthenticationManager.getInstance().getUser().hasRole(Role.MANAGECLASSES))return false;
		User current = AuthenticationManager.getInstance().getUser();
		//Teacher is allowed to edit the class if he's the main teacher or a help teacher
		if(current.data.type == UserType.TEACHER){
			Collection<ClassGroup> cgs = new ArrayList<ClassGroup>();
			cgs.addAll(((Teacher)current).getClasses());
			cgs.addAll(((Teacher)current).getHelpClasses());
			for(ClassGroup c : cgs){
				if(c.id==id)return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param id id of the class
	 * @return the basic breadcrumbs
	 */
	protected static List<Link> getBreadCrumbs(int id){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("classes.list"),"/classes"));
		res.add(new Link(EMessages.get("classes.pupil.title"),"/classes/view/"+id));
		return res;
	}
	
	/**
	 * Does the removing of an  active student from a class. Should be used in a transaction
	 * @param classID id of the class
	 * @param userID id of the student
	 */
	private static void remove(int classID,String userID){
		//Find the ClassPupil object to see if the user is already registered as "previous pupil"
		ClassPupil cp = Ebean.find(ClassPupil.class).where().eq("indid",userID).eq("classid", classID).findUnique();
		if(cp==null){
			//if not, create a new object and save it
			cp = new ClassPupil();
			cp.classid = classID;
			cp.indid = userID;
			cp.save();
		}
		//Remove the class from the "current class" attribute in the user object
		UserModel um = Ebean.find(UserModel.class).where().eq("id", userID).findUnique();
		if(um==null) throw new PersistenceException("Could not find user");
		um.classgroup = null;
		um.update();
	}
	
	/**
	 * Adds the user to the class. Should be used in a transaction
	 * @param classID id of the class
	 * @param userID id of the user
	 */
	private static void add(int classID,String userID){
		UserModel um = Ebean.find(UserModel.class, userID);
		if(um==null) throw new PersistenceException();
		//If the pupil is already in a classgroup, create a new ClassPupil linking (if it doesn't already exist)
		if(um.classgroup!=null){
			ClassPupil cp = Ebean.find(ClassPupil.class).where().eq("classid", um.classgroup).where().eq("indid", userID).findUnique();
			if(cp==null){
				ClassPupil cpNew = new ClassPupil();
				cpNew.classid=um.classgroup;
				cpNew.indid=userID;
				cpNew.save();
			}
		}
		//If the pupil is already linked to the class by a ClassPupil linking, remove the latter
		ClassPupil cpOld = Ebean.find(ClassPupil.class).where().eq("classid", classID).where().eq("indid", userID).findUnique();
		if(cpOld!=null)cpOld.delete();
		um.classgroup=classID;
		um.update();
	}

}
