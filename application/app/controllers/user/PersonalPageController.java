package controllers.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avaje.ebean.Ebean;

import models.data.Link;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.Role;
import play.mvc.Content;
import play.mvc.Results;
import play.data.DynamicForm;
import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.mvc.Result;
import views.html.commons.error;
import views.html.commons.noaccess;
import views.html.login.register;
import views.html.user.showinfo;
import views.html.user.editinfo;
import views.html.user.errorinfo;
import views.html.user.editpass;
import views.html.user.settings;
import controllers.EController;
import controllers.routes;
import controllers.UserController.Register;
import models.EMessages;
import controllers.util.*;

/**
 * Code for Bebras Application by AVANK
 * User: thomas
 */
public class PersonalPageController extends EController {
		
    public static boolean isAuthorized() {
        return AuthenticationManager.getInstance().getUser().hasRole(Role.SETTINGS);
    }

    public static Result show(int tab) {
        ArrayList<Link> breadcrumbs = new ArrayList<Link>();
        Content showpage;
        breadcrumbs.add(new Link(EMessages.get("app.home"),"/"));
        breadcrumbs.add(new Link(EMessages.get("user.pip.settings"),"/settings"));
        if(isAuthorized()){
            showpage = settings.render(EMessages.get("user.pip.personalinfo"), breadcrumbs, tab);
        }else{
            showpage = noaccess.render(breadcrumbs);
        }
        return ok(showpage);
    }

    public static Result changeInformation(){
    	boolean error = false;    	  
        Date bd = new Date();
        UserModel userModel = Ebean.find(UserModel.class).where().eq(
                  "id",AuthenticationManager.getInstance().getUser().getID()).findUnique();

        // get new given information about user and save in data
        DynamicForm editInfo = form().bindFromRequest();
          
        // name
        if(!editInfo.get("fname").equals("")){
            userModel.name = editInfo.get("fname");
            AuthenticationManager.getInstance().getUser().data.name = editInfo.get("fname");
        }else{     	  
            flash("error", EMessages.get(EMessages.get("error.no_input_fname")));
            return Results.redirect(controllers.user.routes.PersonalPageController.show(1)); 
        } 
          
        // email
		if(!InputChecker.getInstance().isCorrectEmail(editInfo.get("email"))){
	        flash("error", EMessages.get(EMessages.get("user.error.wrong_email")));
        	return Results.redirect(controllers.user.routes.PersonalPageController.show(1));
		}else{
            userModel.email = editInfo.get("email");
            AuthenticationManager.getInstance().getUser().data.email = editInfo.get("email");
		}
         
		// bday
        if(!editInfo.get("bday").equals("")){
            try {
                bd = new SimpleDateFormat("MM/dd/yyyy").parse(editInfo.get("bday"));
                Date currentDate = new Date();
                if(bd.after(currentDate)){
                    flash("error", EMessages.get(EMessages.get("error.wrong_date_time")));
                    return Results.redirect(controllers.user.routes.PersonalPageController.show(1));
                }
            } catch (ParseException e) {
            	flash("error", EMessages.get(EMessages.get("error.date")));
            	return Results.redirect(controllers.user.routes.PersonalPageController.show(1));
            }
            userModel.birthdate = bd;
            AuthenticationManager.getInstance().getUser().data.birthdate = bd;
        }else{
        	flash("error", EMessages.get(EMessages.get("error.date")));
        	return Results.redirect(controllers.user.routes.PersonalPageController.show(1));
        }
         
        // gender
        AuthenticationManager.getInstance().getUser().data.preflanguage = editInfo.get("prefLanguage");
        Gender gen = Gender.Female;
        if(editInfo.get("gender").equals("Male")){
            gen = Gender.Male;
        }else if(editInfo.get("gender").equals("Other")){
        	gen = Gender.Other;
        }
        AuthenticationManager.getInstance().getUser().data.gender = gen;
          
        // language
        userModel.preflanguage = editInfo.get("prefLanguage");
        EMessages.setLang(editInfo.get("prefLanguage"));
        userModel.gender = gen;
          
        // save new information in db
        Ebean.save(userModel);

        // success
        flash("success",EMessages.get(EMessages.get("info.successedit")));
        return Results.redirect(controllers.user.routes.PersonalPageController.show(1));
    }
      
    // returns a date in a better readable string
    public static String dateToString(Date dt){
        Calendar cal = new GregorianCalendar();
    	String newdate = new String();
        cal.setTime(dt);
    	newdate = newdate + Integer.toString(cal.get(Calendar.MONTH)+1) + "/" + Integer.toString(cal.get(Calendar.DATE))
    	    + "/" + Integer.toString(cal.get(Calendar.YEAR));
        return newdate;
    }

    public static Result checkValid(){
        return ok();
    }

    public static Result changePassword(){
    	return ok();
    }
      
    public static class Edit{
        @Required
        public String fname;
        @Required
        public String email;
        @Required
        @Formats.DateTime(pattern = "dd/MM/yyyy")
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
