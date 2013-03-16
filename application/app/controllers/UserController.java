package controllers;

import java.util.Map;

import play.mvc.Result;

import scala.collection.mutable.HashMap;
import controllers.user.AbstractUserController;
import controllers.user.AdministratorController;
import controllers.user.IndependentController;
import controllers.user.OrganizerController;
import controllers.user.PupilController;
import controllers.user.Types;
import controllers.user.TeacherController;

/**
 * The purpose of this class is to provide a way to handle all GET request for the user system
 * this class contains a Mapping from userID to User model objects. 
 * Based on the current role that a user is playing his/here request will be routed to a different user controller.
 * current user Controllers are:
 * - Administrator controller.
 * - IndependentController
 * - OrganizerController.
 * - PupilControllerL
 * each of these controller contain domain specific logic to provide the correct view for that current user role
 * each of these controllers MUST contain a list of model objects corresponding with there represented type and they
 * need to use those models to generate a view.
 * @author Sander Demeester
 */
public class UserController {
	
	
	private HashMap<Types, AbstractUserController> userControllerMapping = new HashMap<Types, AbstractUserController>();
	
	public UserController(){
		
		userControllerMapping.put(Types.ADMINISTRATOR,new AdministratorController());
		userControllerMapping.put(Types.ANON,null); //Yes, we need to fix this.
		userControllerMapping.put(Types.INDEPENDENT, new IndependentController());
		userControllerMapping.put(Types.ORGANIZER, new OrganizerController());
		userControllerMapping.put(Types.PUPIL, new PupilController());
		userControllerMapping.put(Types.TEACHER, new TeacherController());
		
	}
	
	public static Result register(){
		//TODO: Delegate to authenticationManager
		return null;
	}
	
	public static Result login(){
		//TODO: Delage to authenticationManager
		return null;
	}
	
	public static Result getLandingPage(String token){ //or whatever the token will be
		//TODO: Delegate to correct UserController object based on token
		/*
		 * The result value will come from some UserController
		 */
		return null;
	}

}
