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
	@SuppressWarnings("deprecation")
	public static Result create(){
		//TODO actually implement
		Form<ClassGroup> f = new Form<ClassGroup>(ClassGroup.class);
		List<Link> bc = getBreadcrumbs();
		OperationResultInfo ori = new OperationResultInfo();
		return ok(createClass.render(f, bc, ori));
	}
	
	public static Result save(){
		//TODO
		return TODO;
	}
	
	/**
	 * 
	 * @return whether the user is authorized to view Classes
	 */
	private static boolean isAuthorized(){
		//TODO actually implement
		
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
