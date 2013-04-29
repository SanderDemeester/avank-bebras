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
import controllers.EController;
import models.EMessages;
import models.data.Link;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.Role;
import play.mvc.Content;
import play.mvc.Results;
import play.data.DynamicForm;
import play.data.validation.Constraints.Required;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;
import views.html.forgotPwd;
import views.html.user.editinfo;
import views.html.user.editpass;
import views.html.user.errorinfo;
import views.html.user.showinfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import views.html.commons.error;
import views.html.commons.noaccess;
import views.html.user.settings;
import controllers.EController;
import controllers.routes;
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

    public static Result checkValid() {
        ArrayList<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("edit_pwd.edit_pwd"), "/passwedit"));
        UserModel userModel = Ebean.find(UserModel.class).where().eq(
                "id", AuthenticationManager.getInstance().getUser().getID()).findUnique();
        DynamicForm editPass = form().bindFromRequest();
        if (editPass.get("new_password").equals(editPass.get("controle_password"))) {
            if (!editPass.get("new_password").equals("")) {
                PasswordHasher.SaltAndPassword hap = null;
                try {
                    hap = PasswordHasher.generateSP(editPass.get("new_password").toCharArray());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userModel.password = hap.password;
                userModel.hash = hap.salt;
                Ebean.save(userModel);
                AuthenticationManager.getInstance().getUser().data.password = hap.password;
                AuthenticationManager.getInstance().getUser().data.hash = hap.salt;
                Ebean.save(userModel);
                flash("success", EMessages.get("edit_pwd.success"));
                return Results.redirect(controllers.user.routes.PersonalPageController.show(2));
            }
        }
        else {
            flash("error", EMessages.get(EMessages.get("forms.error")));
            return Results.redirect(controllers.user.routes.PersonalPageController.show(2));
        }
        if (editPass.hasErrors()) {
            flash("error", EMessages.get(EMessages.get("forms.error")));
            return Results.redirect(controllers.user.routes.PersonalPageController.show(2));
        }
        return Results.redirect(controllers.user.routes.PersonalPageController.show(2));
    }

    public static class Edit {
        @Required
        public String current_password;
        @Required
        public String new_password;
        @Required
        public String controle_passwd;
    }
}
