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
		//Check if form has errors
		if(f.hasErrors()){
			ori.add(EMessages.get("classes.add.notcomplete"),OperationResultInfo.Type.WARNING);
			return badRequest(createClass.render(f, bc, ori));
		}
		ClassGroup cg = f.get();
		try{
			//Check if the school exists
			SchoolModel sm = Ebean.find(SchoolModel.class,cg.schoolid);
			if(sm==null){
				ori.add(EMessages.get("classes.add.noschool"),OperationResultInfo.Type.WARNING);
				return badRequest(createClass.render(f, bc, ori));
			}
			//Check if the grade exists		
			Grade grade = Ebean.find(Grade.class,cg.level);
			if(grade==null){
				ori.add(EMessages.get("classes.add.nograde"),OperationResultInfo.Type.WARNING);
				return badRequest(createClass.render(f, bc, ori));
			}
			cg.teacherid = getTeacher().data.id;
			cg.save();
			flash("success",Integer.toString(cg.id));
			
		}catch(PersistenceException pe){
			//Database error
			ori.add(EMessages.get("classes.add.error"),OperationResultInfo.Type.WARNING);
			return badRequest(createClass.render(f, bc, ori));
		}
		//Return to list page
		return redirect(routes.ClassGroupController.viewClasses(0, "name", "asc", ""));
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
	 * @return the Teacher that is currently logged in. 
	 */
	private static Teacher getTeacher(){
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
	
}
