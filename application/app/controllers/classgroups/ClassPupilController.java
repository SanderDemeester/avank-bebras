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
import models.management.ModelState;
import models.util.OperationResultInfo;
import play.mvc.Result;
import views.html.classes.classpupilManagement;
import views.html.classes.helpteacherManagement;
import views.html.classes.oldClassPupilManagement;
import views.html.commons.noaccess;
import controllers.EController;
import controllers.classgroups.ClassPupilManager.DataSet;

/**
 * @author Jens N. Rammant
 * TODO comments
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

	public static Result editClass(String id){
		//TODO
		return null;
	}
	public static Result viewHelp(String id,int page, String orderBy, String order, String filter){		
		List<Link> breadcrumbs = getBreadCrumbs(id);
		breadcrumbs.add(new Link(EMessages.get("classes.helpteacher.list"),"/classes/"+id+"/help"));
		OperationResultInfo ori = new OperationResultInfo();
		
		int idInt = -1;
		try{
			idInt = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			ori.add(EMessages.get("classes.novalidclassid"),OperationResultInfo.Type.ERROR);
			return ok(
					helpteacherManagement.render(null, null, orderBy, order, filter, breadcrumbs, ori));
		}
		
		//Check if authorized
		if(!isAuthorized(idInt))return ok(noaccess.render(breadcrumbs));
		
		HelpTeacherManager htm = new HelpTeacherManager(idInt, ModelState.READ);
		htm.setFilter(filter);
		htm.setOrder(order);
		htm.setOrderBy(orderBy);
		
		try{
			return ok(
				helpteacherManagement.render(htm.page(page), htm, orderBy, order, filter, breadcrumbs, ori));
		}catch(PersistenceException pe){
			ori.add(EMessages.get("classes.helpteacher.error"),OperationResultInfo.Type.ERROR);
			return ok(
					helpteacherManagement.render(null, htm, orderBy, order, filter, breadcrumbs, ori));
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
	
	private static boolean isAuthorized(int id){
		//TODO
		return true;
	}
	
	/**
	 * 
	 * @param id id of the class
	 * @return the basic breadcrumbs
	 */
	private static List<Link> getBreadCrumbs(String id){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("classes.list"),"/classes"));
		res.add(new Link(EMessages.get("classes.pupil.title"),"/classes/"+id));
		return res;
	}

}
