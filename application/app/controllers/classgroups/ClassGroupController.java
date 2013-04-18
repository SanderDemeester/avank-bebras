/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Grade;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.SchoolModel;
import models.dbentities.UserModel;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.Teacher;
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
		Teacher t = getTeacher();
		MainClassesManager mcm = new MainClassesManager(t.getID(),
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
			cg.teacherid = getTeacher().data.id;
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
	public static Result editClass(String id){
		//Check if valid id
		int classID = -1;
		try{
			classID  = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//TODO
			return TODO;
		}
		List<Link> bc = getBreadcrumbs();
		bc.add(new Link(EMessages.get("classes.pupil.title"),"/classes/view/"+id));
		bc.add(new Link(EMessages.get("classes.edit"),"/classes/view/"+id+"/edit"));
		OperationResultInfo ori = new OperationResultInfo();
		//Check if authorized
		if(!isAuthorized(classID))return ok(noaccess.render(bc));
		//Get class and fill form
		ClassGroup cg = null;
		try{
			cg = Ebean.find(ClassGroup.class,classID);
			int temp = cg.id; //will throw exception if null
		}catch(Exception e){
			//TODO
			return TODO;
		}
		Form<ClassGroup> f = form(ClassGroup.class).bindFromRequest().fill(cg);
		return ok(views.html.classes.editClass.render(f, bc, ori,id));
	}
	
	/**
	 * Saves the updated classgroup
	 * @return the class page
	 */
	public static Result update(String id){
		//TODO check permissions
		int classID = -1;
		try{
			classID = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//TODO
			return TODO;
		}
		List<Link> bc = getBreadcrumbs();
		bc.add(new Link(EMessages.get("classes.pupil.title"),"/classes/view/"+id));
		bc.add(new Link(EMessages.get("classes.edit"),"/classes/view/"+id+"/edit"));
		Form<ClassGroup> f = form(ClassGroup.class).bindFromRequest();
		//Check if form is valid
		OperationResultInfo check = checkForm(f);
		if(check!=null)return badRequest(views.html.classes.editClass.render(f, bc, check,id));
		ClassGroup cg = f.get();
		cg.id = classID;
		try{
			ClassGroup prevVersion = Ebean.find(ClassGroup.class, classID);
			int temp = prevVersion.id; //will throw exception if null
			cg.teacherid = prevVersion.teacherid;
			cg.update();
		}catch(Exception e){
			//TODO
			return TODO;
		}
		return redirect(routes.ClassPupilController.viewClass(id, 0, "name", "asc", ""));
	}
	
	/**
	 * 
	 * @return whether the user is authorized to view Classes
	 */
	private static boolean isAuthorized(){
		//TODO actually implement
		try{
			getTeacher();
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param id of the class
	 * @return whether the user is authorized to edit the class
	 */
	private static boolean isAuthorized(int id){
		//TODO
		return true;
	}
	
	/**
	 * 
	 * @return the Teacher that is currently logged in. 
	 */
	private static Teacher getTeacher(){
		//TODO
		return (Teacher)AuthenticationManager.getInstance().getUser();
		
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
