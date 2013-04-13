/**
 * 
 */
package controllers.classgroups;

import java.util.List;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Link;
import models.dbentities.HelpTeacher;
import models.management.ModelState;
import models.util.OperationResultInfo;
import play.mvc.Result;
import views.html.classes.helpteacherManagement;
import views.html.classes.oldClassPupilManagement;
import views.html.commons.noaccess;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class HelpTeacherController extends ClassPupilController {

	public static Result viewHelp(String id,int page, String orderBy, String order, String filter){		
		List<Link> breadcrumbs = getBreadCrumbs(id);
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
	
	public static Result removeHelp(String id,String helpID){
		//TODO
		List<Link> breadcrumbs = getBreadCrumbs(id);
		OperationResultInfo ori = new OperationResultInfo();
		
		int classID = -1;
		try{
			classID = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//Show empty page with error
			ori.add(EMessages.get("classes.pupil.novalidclassid"),OperationResultInfo.Type.ERROR);
			return ok(
					oldClassPupilManagement.render(null,null,"id","asc","",breadcrumbs,ori)
					);
		}
		
		try{
			HelpTeacher ht =
				Ebean.find(HelpTeacher.class).where().eq("teacherid", helpID).where().eq("classid", classID).findUnique();
			ht.delete();
			flash("deletesuccess","");
		}catch(Exception e){
			flash("deleteerror","");
		}
		
		
		return redirect(routes.HelpTeacherController.viewHelp(id,0,"id","asc",""));
	}
	
	protected static List<Link> getBreadCrumbs(String id){
		List<Link> res = ClassPupilController.getBreadCrumbs(id);
		res.add(new Link(EMessages.get("classes.helpteacher.list"),"/classes/"+id+"/help"));
		return res;
	}
}
