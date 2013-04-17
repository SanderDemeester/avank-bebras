/**
 * 
 */
package controllers.classgroups;

import java.util.List;

import javax.persistence.PersistenceException;
import com.avaje.ebean.Ebean;
import models.EMessages;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.HelpTeacher;
import models.dbentities.UserModel;
import models.management.ModelState;
import models.user.UserType;
import models.util.IDWrapper;
import models.util.OperationResultInfo;
import play.data.Form;
import play.mvc.Result;
import views.html.classes.addHelpTeacher;
import views.html.classes.helpteacherManagement;
import views.html.commons.noaccess;

/**
 * @author Jens N. Rammant
 * TODO confirmation screen
 */
public class HelpTeacherController extends ClassPupilController {

	/**
	 * 
	 * @param id id of the class
	 * @param page page to be shown
	 * @param orderBy what to order the list on
	 * @param order how to order the list
	 * @param filter what to filter on
	 * @return a page with the help teachers
	 */
	public static Result viewHelp(String id,int page, String orderBy, String order, String filter){		
		//Initialize template arguments
		List<Link> breadcrumbs = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();
		
		//check if valid id
		int idInt = -1;
		try{
			idInt = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			return noValidID(breadcrumbs,ori);
		}
		
		//Check if authorized
		if(!isAuthorized(idInt))return ok(noaccess.render(breadcrumbs));
		
		//Configuring manager
		HelpTeacherManager htm = new HelpTeacherManager(idInt, ModelState.READ);
		htm.setFilter(filter);
		htm.setOrder(order);
		htm.setOrderBy(orderBy);
		
		//Try to render the list
		try{
			return ok(
				helpteacherManagement.render(htm.page(page), htm, orderBy, order, filter, breadcrumbs, ori));
		}catch(PersistenceException pe){
			//Show empty page with error
			ori.add(EMessages.get("classes.helpteacher.error"),OperationResultInfo.Type.ERROR);
			return ok(
					helpteacherManagement.render(null, htm, orderBy, order, filter, breadcrumbs, ori));
		}

	}
	
	/**
	 * Remove a help teacher
	 * @param id class id
	 * @param helpID helpteacher to be removed
	 * @return a page with the list of help teachers
	 */
	public static Result removeHelp(String id,String helpID){
		//Initialize template parameters
		List<Link> breadcrumbs = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();
		//Check if valid id
		int classID = -1;
		try{
			classID = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//Show empty page with error
			return noValidID(breadcrumbs,ori);
		}
		
		//Check if authorized
		if(!isAuthorized(classID))return ok(noaccess.render(breadcrumbs));
		//Try to delete the teacher (from helping)
		try{
			HelpTeacher ht =
				Ebean.find(HelpTeacher.class).where().eq("teacherid", helpID).where().eq("classid", classID).findUnique();
			ht.delete();
			flash("deletesuccess","");
		}catch(Exception e){
			flash("deleteerror","");
		}	
		//Redirect to list
		return redirect(routes.HelpTeacherController.viewHelp(id,0,"id","asc",""));
	}
	
	/**
	 * Link a teacher as HelpTeacher
	 * @param id of the class
	 * @return a form to link a teacher
	 */
	public static Result create(String id){
		//Initialize template parameters
		List<Link> bc = getBreadCrumbs(id);
		bc.add(new Link(EMessages.get("classes.helpteacher.add"),"/classes/"+id+"/help/add"));
		OperationResultInfo ori = new OperationResultInfo();
		//Check if valid id
		int classID = -1;
		try{
			classID = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			return noValidID(bc,ori);
		}
		//Check if authorized
		if(!isAuthorized(classID))return ok(noaccess.render(bc));
		//Create and render form
		Form<IDWrapper> f = new Form<IDWrapper>(IDWrapper.class);
		return ok(addHelpTeacher.render(f, bc, ori, id));
	}
	
	/**
	 * Saving of the linking 
	 * @param id class id
	 * @return page with list of HelpTeachers
	 */
	public static Result save(String id){
		//Initialize template arguments
		List<Link> bc = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();
		//Check if valid id
		int classID = -1;
		try{
			classID = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//Show empty page with error
			return noValidID(bc,ori);
		}
		
		//Check if authorized
		if(!isAuthorized(classID))return ok(noaccess.render(bc));
		
		//Retrieve form
		Form<IDWrapper> f = form(IDWrapper.class).bindFromRequest();
		if(f.hasErrors()){
			//If incomplete, show form with warning
			ori.add(EMessages.get("classes.helpteacher.add.incomplete"),OperationResultInfo.Type.WARNING);
			return badRequest(addHelpTeacher.render(f, bc, ori, id));
		}
		//Retrieve id
		IDWrapper i = f.get();
		UserModel um = null;
		//Retrieve usermodel of to be linked teacher
		try{
			um = Ebean.find(UserModel.class, i.id);
		}catch(PersistenceException pe){
			//Retrieval failed, Show form with error
			ori.add(EMessages.get("classes.helpteacher.add.error"),OperationResultInfo.Type.ERROR);
			return badRequest(addHelpTeacher.render(f, bc, ori, id));
		}
		if(um==null){
			//No user with that id exists
			ori.add(EMessages.get("classes.helpteacher.add.usernotexist"),OperationResultInfo.Type.WARNING);
			return badRequest(addHelpTeacher.render(f, bc, ori, id));
		}
		if(um.type!=UserType.TEACHER){
			//User is not a teacher
			ori.add(EMessages.get("classes.helpteacher.add.usernotteacher"),OperationResultInfo.Type.WARNING);
			return badRequest(addHelpTeacher.render(f, bc, ori, id));
		}
		try{
			//Check if teacher is already linked to class as main teacher
			ClassGroup cg = Ebean.find(ClassGroup.class, classID);
			if(cg != null && cg.teacherid.equals(um.id)){
				ori.add(EMessages.get("classes.helpteacher.add.useralreadyteacher"),OperationResultInfo.Type.WARNING);
				return badRequest(addHelpTeacher.render(f, bc, ori, id));
			}
			//Check if teacher is already linked to class as help teacher
			HelpTeacher toSave = Ebean.find(HelpTeacher.class).where().eq("classid",classID).where().eq("teacherid",um.id).findUnique();
			if(toSave!=null){
				ori.add(EMessages.get("classes.helpteacher.add.useralreadyhelp"),OperationResultInfo.Type.WARNING);
				return badRequest(addHelpTeacher.render(f, bc, ori, id));
			}
			//Save new linking
			toSave = new HelpTeacher();
			toSave.classid=classID;
			toSave.teacherid=um.id;
			toSave.save();
		}catch(PersistenceException pe){
			//Saving/retrieval failed, show error
			ori.add(EMessages.get("classes.helpteacher.add.error"),OperationResultInfo.Type.ERROR);
			return badRequest(addHelpTeacher.render(f, bc, ori, id));
		}		
		//redirect to list page
		return redirect(routes.HelpTeacherController.viewHelp(id, 0, "id", "asc", ""));
	}
	/**
	 * 
	 * @param id of the class
	 * @return basic breadcrumbs
	 */
	protected static List<Link> getBreadCrumbs(String id){
		List<Link> res = ClassPupilController.getBreadCrumbs(id);
		res.add(new Link(EMessages.get("classes.helpteacher.list"),"/classes/"+id+"/help"));
		return res;
	}
	/**
	 * 
	 * @param bc breadcrumbs
	 * @param ori operationresultinfo
	 * @return the page to be returned when the id isn't valid
	 */
	private static Result noValidID(List<Link> bc,OperationResultInfo ori){
		ori.add(EMessages.get("classes.novalidclassid"),OperationResultInfo.Type.ERROR);
		return ok(
				helpteacherManagement.render(null,null,"id","asc","",bc,ori)
				);
	}
	
}
