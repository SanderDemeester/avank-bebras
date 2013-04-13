/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.ClassPupil;
import models.dbentities.UserModel;
import models.management.ModelState;
import models.util.OperationResultInfo;
import play.mvc.Result;
import views.html.classes.classpupilManagement;
import views.html.classes.oldClassPupilManagement;
import views.html.commons.noaccess;
import controllers.EController;
import controllers.classgroups.ClassPupilManager.DataSet;

/**
 * @author Jens N. Rammant
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
	public static Result viewClass(String id,int page, String orderBy, String order, String filter){
		//Setting up template arguments
		List<Link> breadcrumbs = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();
		
		//Parse ID to int
		int idInt = -1;
		try{
			idInt = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//Return empty page with error
			ori.add(EMessages.get("classes.novalidclassid"),OperationResultInfo.Type.ERROR);
			return ok(
					classpupilManagement.render(null,null,orderBy,order,filter,breadcrumbs,ori,null)
					);
		}
		//Check if authorized
		if(!isAuthorized(idInt))return ok(noaccess.render(breadcrumbs));
		
		//Fetch the class
		ClassGroup cg = null;
		try{
			cg = Ebean.find(ClassGroup.class).where().eq("id", idInt).findUnique();
		}catch(PersistenceException pe) {
			cg = null;
		}
		//Determine which subset of students to be shown on the main page (active-->only active, not active --> all)
		DataSet ds = ClassPupilManager.DataSet.ALL;
		if(cg!=null && cg.isActive())ds=ClassPupilManager.DataSet.ACTIVE;
		//Configure the manager
		ClassPupilManager cpm = new ClassPupilManager(idInt, ds,
				ModelState.READ);
		cpm.setFilter(filter);
		cpm.setOrder(order);
		cpm.setOrderBy(orderBy);
		//Allow removal of students is class is active
		if(cg!=null && cg.isActive())cpm.setCanRemove(true);
		//Try to render the list
		try{
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
	 * 
	 * @param id id of the class
	 * @return an edit page for the class
	 */
	public static Result editClass(String id){
		//TODO
		return null;
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
	public static Result viewOldPupils(String id,int page, String orderBy, String order, String filter){
		//Setting up template arguments
		List<Link> breadcrumbs = getBreadCrumbs(id);
		breadcrumbs.add(new Link(EMessages.get("classes.pupil.oldpupillist"),"/classes/"+id+"/old"));
		OperationResultInfo ori = new OperationResultInfo();
			
		//Parse ID to int
		int idInt = -1;
		try{
			idInt = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//Show empty page with error
			ori.add(EMessages.get("classes.pupil.novalidclassid"),OperationResultInfo.Type.ERROR);
			return ok(
					oldClassPupilManagement.render(null,null,orderBy,order,filter,breadcrumbs,ori)
					);
		}
		//Check if authorized
		if(!isAuthorized(idInt))return ok(noaccess.render(breadcrumbs));
		//Configure manager
		ClassPupilManager cpm = new ClassPupilManager(idInt, ClassPupilManager.DataSet.NOTACTIVE,
				ModelState.READ);
		cpm.setFilter(filter);
		cpm.setOrder(order);
		cpm.setOrderBy(orderBy);
		//Try to render the list
		try{
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
	
	public static Result removeStudent(String classID,String pupilID){		
		//Setting up template arguments
		List<Link> breadcrumbs = getBreadCrumbs(classID);
		OperationResultInfo ori = new OperationResultInfo();
		
		//Parse ID to int
		int idInt = -1;
		try{
			idInt = Integer.parseInt(classID);
		}catch(NumberFormatException nfe){
			//Return empty page with error
			ori.add(EMessages.get("classes.novalidclassid"),OperationResultInfo.Type.ERROR);
			return ok(
					classpupilManagement.render(null,null,"id","asc","",breadcrumbs,ori,null)
					);
		}
		//Check if authorized
		if(!isAuthorized(idInt))return ok(noaccess.render(breadcrumbs));
		
		//Do the actual deleting from the class. Needs to be in a transaction
		Ebean.beginTransaction();
		try{
			remove(idInt,pupilID);
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
	 * @param id the id of the class
	 * @return whether the current user is authorized to view/edit this class
	 */
	protected static boolean isAuthorized(int id){
		//TODO
		return true;
	}
	
	/**
	 * 
	 * @param id id of the class
	 * @return the basic breadcrumbs
	 */
	protected static List<Link> getBreadCrumbs(String id){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("classes.list"),"/classes"));
		res.add(new Link(EMessages.get("classes.pupil.title"),"/classes/"+id));
		return res;
	}
	
	/**
	 * Does the removing of an  active student from a class
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

}
