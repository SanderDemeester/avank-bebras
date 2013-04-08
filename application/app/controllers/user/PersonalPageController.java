package controllers.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.data.Link;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;
import models.user.Gender;
import play.mvc.Content;
import play.mvc.Results;
import play.data.DynamicForm;
import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.mvc.Result;
import views.html.error;
import views.html.user.showinfo;
import views.html.user.editinfo;
import views.html.user.errorinfo;
import views.html.user.editpass;
import controllers.EController;
import controllers.routes;
import controllers.UserController.Register;


/**
 * Code for Bebras Application by AVANK
 * User: thomas
 */
public class PersonalPageController extends EController {
		
	  public static Result show() {
		  	ArrayList<Link> breadcrumbs = new ArrayList<Link>();
		  	Content showpage;
		  	breadcrumbs.add(new Link("Home","/"));
		  	breadcrumbs.add(new Link("Personal information","/personal"));
		  	if(AuthenticationManager.getInstance().isLoggedIn()){
		  		showpage = showinfo.render("Personal information", breadcrumbs);
		  	}else{
		  		showpage = errorinfo.render("Information is not available",breadcrumbs);
		  	}
		    return ok(showpage);
	  }
	  
	  public static Result edit() {
		  ArrayList<Link> breadcrumbs = new ArrayList<Link>();
		  Content showpage;
		  breadcrumbs.add(new Link("Home","/"));
		  breadcrumbs.add(new Link("Edit information","/personaledit"));
		  if(AuthenticationManager.getInstance().isLoggedIn()){
			  showpage = editinfo.render("Edit information",breadcrumbs);
	  	  }else{
	  		  showpage = errorinfo.render("Information is not available",breadcrumbs);
	  	  } 
	  	  return ok(showpage);
	  }
	  
	  public static Result changeInformation(){
		  	Date bd = new Date();
		  	
			UserModel userModel = Ebean.find(UserModel.class).where().eq(
					"id",AuthenticationManager.getInstance().getUser().getID()).findUnique();
		  	
		  	// get new given information about user and save in data
			DynamicForm editInfo = form().bindFromRequest();
			if(!editInfo.get("fname").equals("")){
				userModel.name = editInfo.get("fname");
				AuthenticationManager.getInstance().getUser().data.name = editInfo.get("fname");
			}
			if(!editInfo.get("email").equals("")){
				userModel.name = editInfo.get("email");
				AuthenticationManager.getInstance().getUser().data.email = editInfo.get("email");
			}
			if(!editInfo.get("bday").equals("")){
				try {
					bd = new SimpleDateFormat("yyyy-mm-dd").parse(editInfo.get("bday"));
				} catch (ParseException e) {
					e.getMessage();
				}
				userModel.birthdate = bd;
				AuthenticationManager.getInstance().getUser().data.birthdate = bd;
			}
			AuthenticationManager.getInstance().getUser().data.preflanguage = editInfo.get("prefLanguage");
			Gender gen = Gender.Female;
			if(editInfo.get("gender").equals("Male")){
				gen = Gender.Male;
			}
			AuthenticationManager.getInstance().getUser().data.gender = gen;
			userModel.preflanguage = editInfo.get("prefLanguage");
			userModel.gender = gen;
			Ebean.save(userModel);
			
			// redirect information page
		  	return Results.redirect(controllers.user.routes.PersonalPageController.show());
	  }
	  
	  public static Result checkValid(){
		  return ok();
	  }
	  
	  public static Result changePassword(){
		  	ArrayList<Link> breadcrumbs = new ArrayList<Link>();
		  	Content showpage;
		  	breadcrumbs.add(new Link("Home","/"));
		  	breadcrumbs.add(new Link("Edit password","/passwedit"));
		  	if(AuthenticationManager.getInstance().isLoggedIn()){
		  		showpage = editpass.render("Edit password", breadcrumbs);
		  	}else{
		  		showpage = errorinfo.render("Information is not available",breadcrumbs);
		  	}
		    return ok(showpage);
	  }
	  
	  public static class Edit{
			@Required
			public String fname;
			@Required
			public String email;
			@Required
			@Formats.DateTime(pattern = "yyyy/dd/mm")
			public String bday;
			@Required
			public String password;
			@Required
			public String controle_passwd;
			@Required
			public String gender;
			@Required
			public String prefLanguage;
	  }
}
