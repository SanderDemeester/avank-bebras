package controllers.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.avaje.ebean.Ebean;
import controllers.EController;
import models.EMessages;
import models.data.Link;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;
import models.user.Gender;
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
import views.html.user.settings;
import controllers.EController;
import controllers.routes;
import controllers.UserController.Register;
import models.EMessages;


/**
 * Code for Bebras Application by AVANK
 * User: thomas
 */
public class PersonalPageController extends EController {

      public static Result show() {
    	  	  ArrayList<Link> breadcrumbs = new ArrayList<Link>();
              Content showpage;
              breadcrumbs.add(new Link(EMessages.get("app.home"),"/"));
              breadcrumbs.add(new Link(EMessages.get("user.pip.personalinfo"),"/personalsettings"));
              if(AuthenticationManager.getInstance().isLoggedIn()){
                  showpage = settings.render(EMessages.get("user.pip.personalinfo"), breadcrumbs);
              }else{
                  showpage = errorinfo.render(EMessages.get("error.no_info"),breadcrumbs);
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
        	  return Results.redirect(controllers.user.routes.PersonalPageController.show()); 
          } 
          
          // email
          if(!editInfo.get("email").equals("")){
              userModel.email = editInfo.get("email");
              AuthenticationManager.getInstance().getUser().data.email = editInfo.get("email");
          }else{
        	  flash("error", EMessages.get(EMessages.get("error.no_input_email")));
        	  return Results.redirect(controllers.user.routes.PersonalPageController.show());
          }
          // bday
          if(!editInfo.get("bday").equals("")){
              try {
                  bd = new SimpleDateFormat("MM/dd/yyyy").parse(editInfo.get("bday"));
                  Date currentDate = new Date();
                  if(bd.after(currentDate)){
                      flash("error", EMessages.get(EMessages.get("error.wrong_date_time")));
                      return Results.redirect(controllers.user.routes.PersonalPageController.show());
                  }
              } catch (ParseException e) {
            	  flash("error", EMessages.get(EMessages.get("error.date")));
            	  return Results.redirect(controllers.user.routes.PersonalPageController.show());
              }
              userModel.birthdate = bd;
              AuthenticationManager.getInstance().getUser().data.birthdate = bd;
          }else{
        	  flash("error", EMessages.get(EMessages.get("error.date")));
        	  return Results.redirect(controllers.user.routes.PersonalPageController.show());
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
          return Results.redirect(controllers.user.routes.PersonalPageController.show());
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
                userModel.password = editPass.get("new_password");
                AuthenticationManager.getInstance().getUser().data.password = editPass.get("new_password");
                Ebean.save(userModel);
                flash("success", EMessages.get("edit_pwd.success"));
                return Results.redirect(controllers.user.routes.PersonalPageController.show());
            }
        }
        else {
            flash("error", EMessages.get(EMessages.get("forms.error")));
            return badRequest(editpass.render((EMessages.get("edit_pwd.edit_pwd")), breadcrumbs));
        }
        if (editPass.hasErrors()) {
            flash("error", EMessages.get(EMessages.get("forms.error")));
            return badRequest(editpass.render((EMessages.get("edit_pwd.edit_pwd")), breadcrumbs));
        }
        return Results.redirect(controllers.user.routes.PersonalPageController.show());
    }

    public static Result changePassword() {
        ArrayList<Link> breadcrumbs = new ArrayList<Link>();
        Content showpage;
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("edit_pwd.edit_pwd"), "/passwedit"));
        if (AuthenticationManager.getInstance().isLoggedIn()) {
            showpage = editpass.render(EMessages.get("edit_pwd.edit_pwd"), breadcrumbs);
        } else {
            showpage = errorinfo.render("Information is not available", breadcrumbs);
        }
        return ok(showpage);
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
