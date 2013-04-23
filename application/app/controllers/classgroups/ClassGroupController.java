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
import models.data.Grade;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.SchoolModel;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.Role;
import models.user.Teacher;
import models.user.User;
import models.user.UserType;
import models.util.OperationResultInfo;

import play.data.Form;
import play.mvc.Result;
import views.html.classes.classManagement;
import views.html.classes.createClass;
import views.html.commons.noaccess;
import controllers.EController;

/**
 * @author Jens N. Rammant
 */
public class ClassGroupController extends EController {

	/**
	 * 
	 * @param page which page of classes to show
	 * @param orderBy what to order the classes in
	 * @param order what order to use
	 * @param filter what filter to use
	 * @return a page that lists all the classes of the teacher
	 */
	public static Result viewClasses(int page, String orderBy, String order, String filter){
		//Initialize template argument
		List<Link> breadcrumbs = getBreadcrumbs();
		OperationResultInfo ori = new OperationResultInfo();
		//Check if authorized
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		//Configure the manager
		String teacherID =  (getTeacher()==null)?"!!NoTeacher!!":getTeacher().getID();
		MainClassesManager mcm = new MainClassesManager(teacherID,
				ModelState.READ);
		mcm.setFilter(filter);
		mcm.setOrder(order);
		mcm.setOrderBy(orderBy);
		//Try to render the list
		try{
			return ok(
					classManagement.render(mcm.page(page), mcm, orderBy, order, filter, breadcrumbs,ori));
		}catch(PersistenceException pe){
			//Show empty page with error
			ori.add(EMessages.get("classes.main.listerror"), OperationResultInfo.Type.ERROR);
					return ok(classManagement.render(null, mcm, orderBy, order, filter, breadcrumbs,ori));
				}
	}
	
	/**
	 * 
	 * @return a creation page for a new class
	 */
	public static Result create(){
		//Initialize template arguments
		List<Link> bc = getBreadcrumbs();
		bc.add(new Link(EMessages.get("classes.add"),"/classes/add" ));
		OperationResultInfo ori = new OperationResultInfo();
		Form<ClassGroup> f = new Form<ClassGroup>(ClassGroup.class);
		//Check if authorized
		if(!isAuthorized())return ok(noaccess.render(bc));
		//Show form		
		return ok(createClass.render(f, bc, ori));
	}
	
	/**
	 * Saves the form and then return the list of classes page (if possible to save form, otherwise redirected
	 * to the form
	 * @return the page with the list of classes
	 */
	public static Result save(){
		//Initialize template arguments
		List<Link> bc = getBreadcrumbs();
		bc.add(new Link(EMessages.get("classes.add"),"/classes/add" ));
		OperationResultInfo ori = new OperationResultInfo();
		//Check if authorized
		if(!isAuthorized())return ok(noaccess.render(bc));
		//Get form from request
		Form<ClassGroup> f = form(ClassGroup.class).bindFromRequest();
		
		try{
			//Check if form is valid
			OperationResultInfo check = checkForm(f);
			if(check!=null) return badRequest(createClass.render(f, bc, check));
			//Get classgroup and add teacher data
			ClassGroup cg = f.get();
			//getTeacher() should never be null, but it's a safeguard
			cg.teacherid = (getTeacher()==null)?"!!NoTeacher!!":getTeacher().data.id;
			cg.save();
			flash("success",Integer.toString(cg.id));
			
		}catch(PersistenceException pe){
			//Database error
			ori.add(EMessages.get("classes.add.error"),OperationResultInfo.Type.ERROR);
			return badRequest(createClass.render(f, bc, ori));
		}
		//Return to list page
		return redirect(routes.ClassGroupController.viewClasses(0, "name", "asc", ""));
	}
	

	/**
	 * 
	 * @param id id of the class
	 * @return an edit page for the class
	 */
	public static Result editClass(int id){
		OperationResultInfo ori = new OperationResultInfo();
		List<Link> bc = getBreadcrumbs();
		bc.add(new Link(EMessages.get("classes.pupil.title"),"/classes/view/"+id));
		bc.add(new Link(EMessages.get("classes.edit"),"/classes/view/"+id+"/edit"));
		try{
			//Check if authorized
			if(!isAuthorized(id))return ok(noaccess.render(bc));
			//Get class and fill form
			ClassGroup cg = null;
		
			cg = Ebean.find(ClassGroup.class,id);
			@SuppressWarnings("unused")
			int temp = cg.id; //will throw exception if null
			Form<ClassGroup> f = form(ClassGroup.class).bindFromRequest().fill(cg);
			return ok(views.html.classes.editClass.render(f, bc, ori,id));
		}catch(Exception e){
			ori.add(EMessages.get("classes.add.error"),OperationResultInfo.Type.ERROR);
			return ok(
					views.html.classes.editClass.render(null, bc, ori,id)
					);
		}
		
	}
	
	/**
	 * Saves the updated classgroup
	 * @return the class page
	 */
	public static Result update(int id){
		List<Link> bc = getBreadcrumbs();
		bc.add(new Link(EMessages.get("classes.pupil.title"),"/classes/view/"+id));
		bc.add(new Link(EMessages.get("classes.edit"),"/classes/view/"+id+"/edit"));		
		OperationResultInfo ori = new OperationResultInfo();
		Form<ClassGroup> f = form(ClassGroup.class).bindFromRequest();
		try{
			//Check if authorized
			if(!isAuthorized(id))return ok(noaccess.render(bc));			
			
			//Check if form is valid
			OperationResultInfo check = checkForm(f);
			if(check!=null)return badRequest(views.html.classes.editClass.render(f, bc, check,id));
			//Retrieve ClassGroup from form and set id to the correct id
			ClassGroup cg = f.get();
			cg.id = id;
			
			ClassGroup prevVersion = Ebean.find(ClassGroup.class, id);
			@SuppressWarnings("unused")
			int temp = prevVersion.id; //will throw exception if null
			//Set teacherid to the old teacher id
			cg.teacherid = prevVersion.teacherid;
			cg.update();
		}catch(Exception e){
			ori.add(EMessages.get("classes.add.error"),OperationResultInfo.Type.ERROR);
			return ok(
					views.html.classes.editClass.render(f, bc, ori,id)
					);
		}
		return redirect(routes.ClassPupilController.viewClass(id, 0, "name", "asc", ""));
	}
	
	/**
	 * 
	 * @return whether the user is authorized to view Classes
	 */
	public static boolean isAuthorized(){
		return AuthenticationManager.getInstance().getUser().hasRole(Role.MANAGECLASSES);
	}
	/**
	 * 
	 * @param id id of the class
	 * @return whether the user is authorized to edit the class
	 * @throws PersistenceException when something goes wrong with the db
	 */
	private static boolean isAuthorized(int id) throws PersistenceException{
		if(!isAuthorized())return false;
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
	 * @return the Teacher that is currently logged in. Null if it isn't a teacher
	 */
	private static Teacher getTeacher(){
		try{
			return (Teacher)AuthenticationManager.getInstance().getUser();
		}catch(Exception e){
			return null;
		}
		
	}
	
	/**
	 * 
	 * @return the basic breadcrumbs
	 */
	public static List<Link> getBreadcrumbs(){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("classes.list"),"/classes"));
		return res;
	}
	
	/**
	 * Checks if a ClassGroup form is valid
	 * @param f Form to be checked
	 * @return null is form is okay, otherwise an ORI with a message to be shown
	 * @throws PersistenceException if something goes wrong with the db.
	 */
	private static OperationResultInfo checkForm(Form<ClassGroup> f) throws PersistenceException{
		OperationResultInfo ori = new OperationResultInfo();
		//Check if form has errors, like required fields empty etc...
		if(f.hasErrors()){
			ori.add(EMessages.get("classes.add.notcomplete"),OperationResultInfo.Type.WARNING);
			return ori;
		}
		ClassGroup cg = f.get();
		SchoolModel sm = Ebean.find(SchoolModel.class,cg.schoolid);
		if(sm==null){
			ori.add(EMessages.get("classes.add.noschool"),OperationResultInfo.Type.WARNING);
			return ori;
		}
		//Check if the grade exists		
		Grade grade = Ebean.find(Grade.class,cg.level);
		if(grade==null){
			ori.add(EMessages.get("classes.add.nograde"),OperationResultInfo.Type.WARNING);
			return ori;
		}
		
		return null;
	}
	
}
