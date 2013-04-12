/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import models.EMessages;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.UserModel;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.Teacher;
import models.user.UserType;
import models.util.OperationResultInfo;

import play.mvc.Result;
import views.html.classes.classManagement;
import views.html.commons.noaccess;
import controllers.EController;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class ClassGroupController extends EController {

	public static Result viewClasses(int page, String orderBy, String order, String filter){
		List<Link> breadcrumbs = getBreadcrumbs();
		OperationResultInfo ori = new OperationResultInfo();
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		Teacher t = getTeacher();
		MainClassesManager mcm = new MainClassesManager(t.getID(),
				ModelState.READ);
		mcm.setFilter(filter);
		mcm.setOrder(order);
		mcm.setOrderBy(orderBy);
		
		try{
			return ok(
					classManagement.render(mcm.page(page), mcm, orderBy, order, filter, breadcrumbs,ori));
		}catch(PersistenceException pe){
			ori.add(EMessages.get("classes.main.listerror"), OperationResultInfo.Type.ERROR);
					return ok(classManagement.render(null, mcm, orderBy, order, filter, breadcrumbs,ori));
				}
	}
	
	private static boolean isAuthorized(){
		//TODO
		return true;
	}
	
	private static Teacher getTeacher(){
		//TODO
		//return (Teacher)AuthenticationManager.getInstance().getUser();
		return new Teacher(new UserModel("1", UserType.TEACHER, "", new Date(17), new Date(17), "", "", "", Gender.Female, ""));
		
	}
	
	public static List<Link> getBreadcrumbs(){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("classes.list"),"/classes"));
		return res;
	}
	
}
