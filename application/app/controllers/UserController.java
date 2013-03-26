package controllers;

import java.util.ArrayList;
import java.util.Map;

import models.data.Link;
import models.user.AuthenticationManager;

import play.data.Form;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results.Redirect;

import scala.collection.mutable.HashMap;
import models.data.Link;
import views.html.index;
import views.html.landingPages.AdminLandingPage;
import views.html.landingPages.IndependentPupilLandingPage;
import views.html.landingPages.OrganizerLandingPage;
import views.html.landingPages.PupilLandingPage;
import views.html.register;
import views.html.emptyPage;
import controllers.user.Type;

/**
 * This class receives all GET requests and based on there session identifier (cookie)
 * and current role in the system they will be served a different view.
 * @author Sander Demeester
 */
public class UserController extends EController{

    /**
     * This hashmap embodies the mapping from a Type to a view.
     * Each view is responsible for getting all information from the DataModel and make a
     * beautiful view for the user :)
     */
    private HashMap<Type, Class<?>> landingPageHashmap = new HashMap<Type, Class<?>>();
    private AuthenticationManager authenticatieManger = new AuthenticationManager();


    public UserController(){

        landingPageHashmap.put(Type.ADMINISTRATOR, AdminLandingPage.class);
        landingPageHashmap.put(Type.INDEPENDENT, IndependentPupilLandingPage.class);
        landingPageHashmap.put(Type.ORGANIZER, OrganizerLandingPage.class);
        landingPageHashmap.put(Type.PUPIL,PupilLandingPage.class);


    }
    /**
     * This methode gets requested when the user clicks on "signup".
     * @return Result page.
     */
    public static Result signup(){
        //TODO: Delegate to authenticationManager
    	setCommonHeaders();
    	 return ok(register.render("Registration", 
    			 new ArrayList<Link>(),
    			 form(Register.class)
    			 ));
    }
    /**
     * this methode is called when the user submits his/here register information.
     * @return Result page
     */
    public static Result register(){
    	setCommonHeaders();
    	Form<Register> registerForm = form(Register.class).bindFromRequest();
    	String r = new String();
    	r += registerForm.get().email + "\n";
    	r += registerForm.get().fname + "\n";
    	r += registerForm.get().lname + "\n";
    	r += registerForm.get().password;
    	return ok(emptyPage.render("Succes", new ArrayList<Link>(), r));
    }

    public static Result login(){
        //TODO: Delage to authenticationManager
        return null;
    }

    public static Result logout(){
        //TODO: Tell authenticationManager to log a user out.

        setCommonHeaders();
        return null;
    }

    public static Result getLandingPage(String token){ //or whatever the token will be
        //TODO: Delegate to correct UserController object based on token
        /*
         * The result will come from the landingPageHashMap
         */
        return null;
    }
    
    public static class Register{
    	public String fname;
    	public String lname;
    	public String email;
    	public String bday;
    	public String password;
    	public String controle_passwd;
    }

}
