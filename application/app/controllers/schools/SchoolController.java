/**
 * 
 */
package controllers.schools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import models.EMessages;
import models.data.Link;
import models.dbentities.SchoolModel;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.Teacher;
import models.user.User;
import models.user.UserType;
import models.util.OperationResultInfo;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Results;
import views.html.commons.noaccess;
import views.html.schools.addschool;
import views.html.schools.schools;
import controllers.EController;

/**
 * @author Jens N. Rammant
 * TODO comments
 * TODO add link to teacher
 */
public class SchoolController extends EController {

	public static Result viewSchools(){
		//Generate breadcrumbs & template arguments
		ArrayList<Link> breadcrumbs = getBreadcrumbs();
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		
		OperationResultInfo ori = new OperationResultInfo();
		ArrayList<SchoolModel> list = new ArrayList<SchoolModel>();
		
		Teacher t = getTeacher();
		try{
			list.addAll(t.getSchools());
		}catch(PersistenceException pe){
			list.clear();
			ori.add(EMessages.get("schools.list.error"), OperationResultInfo.Type.ERROR);
		}
		//TODO: possible improvement: use DMTV (or at least make it look similar)
		return ok(schools.render(breadcrumbs,ori,list));
	}
	
	 public static Result create(){
		 List<Link> breadcrumbs = getBreadcrumbs();
	     breadcrumbs.add(new Link(EMessages.get("schools.add"),"/schools/new"));
	     if(!isAuthorized())return ok(noaccess.render(breadcrumbs)); //Check if authorized
	     
	     OperationResultInfo ori = new OperationResultInfo();
	     
	     Form<SchoolModel> form = new Form<SchoolModel>(SchoolModel.class);
		 return ok(addschool.render(form, breadcrumbs, ori));
	 }
	 
	 public static Result save(){
		//Generate breadcrumbs
	        List<Link> breadcrumbs = getBreadcrumbs();
	        breadcrumbs.add(new Link(EMessages.get("schools.add"),"/schools/new"));
	        if(!isAuthorized())return ok(noaccess.render(breadcrumbs)); //Check if authorized

	        //Retrieve the form
	        Form<SchoolModel> form = form(SchoolModel.class).bindFromRequest();
	        if(form.hasErrors()) {
	            //Form was not complete --> return form with a warning
	            OperationResultInfo ori = new OperationResultInfo();
	            ori.add(EMessages.get("schools.error.notcomplete"), OperationResultInfo.Type.WARNING);
	            return badRequest(addschool.render(form, breadcrumbs, ori));
	        }
	        //Try to save the info
	        SchoolModel m = form.get();
	        try{
	        	Teacher t = getTeacher();
	        	m.orig = t.data.id;
	            m.save();
	        }catch(Exception p){
	            //Something went wrong in the saving. Redirect back to the create page with an error alert
	            OperationResultInfo ori = new OperationResultInfo();
	            ori.add(EMessages.get("schools.error.savefail"), OperationResultInfo.Type.ERROR);
	            return badRequest(addschool.render(form, breadcrumbs, ori));
	        }
	        //Redirect back to the list
	        flash("success",Integer.toString(m.id));
	        return Results.redirect(controllers.schools.routes.SchoolController.viewSchools());
	        

	 }
	
	public static ArrayList<Link> getBreadcrumbs(){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("schools.title"),"/schools"));
		return res;
	}
	
	private static boolean isAuthorized(){
		//TODO
		//return AuthenticationManager.getInstance().getUser().data.type == UserType.TEACHER;
		return true;
	}
	
	private static Teacher getTeacher(){
		//TODO
		//Teacher t = (Teacher)AuthenticationManager.getInstance().getUser();		
		return new Teacher(new UserModel("1",UserType.TEACHER, "aa", new Date(17), new Date(17), "aa", "aa", "aa", Gender.Other, "gg"));
				
	}
}
