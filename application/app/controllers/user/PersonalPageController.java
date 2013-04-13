package controllers.user;

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
import views.html.user.editinfo;
import views.html.user.editpass;
import views.html.user.errorinfo;
import views.html.user.showinfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Code for Bebras Application by AVANK
 * User: thomas
 */
public class PersonalPageController extends EController {

    public static Result show() {
        ArrayList<Link> breadcrumbs = new ArrayList<Link>();
        Content showpage;
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Personal information", "/personal"));
        if (AuthenticationManager.getInstance().isLoggedIn()) {
            showpage = showinfo.render("Personal information", breadcrumbs);
        } else {
            showpage = errorinfo.render("Information is not available", breadcrumbs);
        }
        return ok(showpage);
    }

    public static Result edit() {
        ArrayList<Link> breadcrumbs = new ArrayList<Link>();
        Content showpage;
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Edit information", "/personaledit"));
        if (AuthenticationManager.getInstance().isLoggedIn()) {
            showpage = editinfo.render("Edit information", breadcrumbs);
        } else {
            showpage = errorinfo.render("Information is not available", breadcrumbs);
        }
        return ok(showpage);
    }

    public static Result changeInformation() {
        Date bd = new Date();

        UserModel userModel = Ebean.find(UserModel.class).where().eq(
                "id", AuthenticationManager.getInstance().getUser().getID()).findUnique();

        // get new given information about user and save in data
        DynamicForm editInfo = form().bindFromRequest();
        if (!editInfo.get("fname").equals("")) {
            userModel.name = editInfo.get("fname");
            AuthenticationManager.getInstance().getUser().data.name = editInfo.get("fname");
        }
        if (!editInfo.get("email").equals("")) {
            userModel.name = editInfo.get("email");
            AuthenticationManager.getInstance().getUser().data.email = editInfo.get("email");
        }
        if (!editInfo.get("bday").equals("")) {
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
        if (editInfo.get("gender").equals("Male")) {
            gen = Gender.Male;
        }
        AuthenticationManager.getInstance().getUser().data.gender = gen;
        userModel.preflanguage = editInfo.get("prefLanguage");
        userModel.gender = gen;
        Ebean.save(userModel);

        // redirect information page
        return Results.redirect(controllers.user.routes.PersonalPageController.show());
    }

    public static Result checkValid() {
        UserModel userModel = Ebean.find(UserModel.class).where().eq(
                "id", AuthenticationManager.getInstance().getUser().getID()).findUnique();
        DynamicForm editPass = form().bindFromRequest();
        if (editPass.get("new_password").equals(editPass.get("controle_password"))) {
            if (!editPass.get("new_password").equals("")) {
                userModel.password = editPass.get("new_password");
                AuthenticationManager.getInstance().getUser().data.password = editPass.get("new_password");
                Ebean.save(userModel);
            }
        }
        else {
            return badRequest(views.html.commons.error.render(new ArrayList<Link>(), "Error", "This email address is not valid"));
        }
        if (editPass.hasErrors()) {
            return badRequest(views.html.commons.error.render(new ArrayList<Link>(), "Error", "Invalid request"));
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
