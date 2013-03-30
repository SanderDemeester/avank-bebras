package controllers;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.format.datetime.DateFormatter;

import com.avaje.ebean.Ebean;

import models.data.Link;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.UserType;
import models.user.UserID;
import play.data.Form;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results.Redirect;
import scala.collection.mutable.HashMap;
import models.dbentities.UserModel;
import views.html.index;
import views.html.landingPages.AdminLandingPage;
import views.html.landingPages.IndependentPupilLandingPage;
import views.html.landingPages.OrganizerLandingPage;
import views.html.landingPages.PupilLandingPage;
import views.html.register;
import views.html.registerLandingPage;
import views.html.login;
import views.html.loginLandingPage;

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
	private HashMap<UserType, Class<?>> landingPageHashmap = new HashMap<UserType, Class<?>>();
	private AuthenticationManager authenticatieManger = AuthenticationManager.getInstance();

	public UserController(){
		landingPageHashmap.put(UserType.ADMINISTRATOR, AdminLandingPage.class);
		landingPageHashmap.put(UserType.INDEPENDENT, IndependentPupilLandingPage.class);
		landingPageHashmap.put(UserType.ORGANIZER, OrganizerLandingPage.class);
		landingPageHashmap.put(UserType.PUPIL,PupilLandingPage.class);
	}
	/**
	 * This methode gets requested when the user clicks on "signup".
	 * @return Result page.
	 */
	public static Result signup(){
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
		SecureRandom random = null;
		SecretKeyFactory secretFactory = null;
		byte[] passwordByteString = null;
		String passwordHEX = "";
		String saltHEX = "";
		Date birtyDay = new Date();
		
		
		//Zijn de 2 eerste letters van uw voornaam en de 7 of MAX letters van uw achternaam.
		String bebrasID = null; 
		
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {}

		byte[] salt = new byte[16]; //RSA PKCS5
		
		
		random.nextBytes(salt);
		
		KeySpec PBKDF2 = new PBEKeySpec(registerForm.get().password.toCharArray(), salt, 1000, 160);

		try{
			secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		}catch(Exception e){}


		try {
			passwordByteString = secretFactory.generateSecret(PBKDF2).getEncoded();
		} catch (InvalidKeySpecException e) {}
		try{
			saltHEX = new String(Hex.encodeHex(salt));
			passwordHEX = new String(Hex.encodeHex(passwordByteString));
			birtyDay = new SimpleDateFormat("yyyy/dd/mm").parse(registerForm.get().bday);
		}catch(Exception e){}
		
		bebrasID = registerForm.get().fname.toLowerCase().substring(0,2);
		bebrasID += registerForm.get().lname.toLowerCase().substring(0, registerForm.get().lname.length() < 7 ? registerForm.get().lname.length() : 7);
		
		String r = "Welkom ";
		r += registerForm.get().fname + "!";

		/*
		 * There needs to be some more logic here for generating bebras ID's 
		 * Save user object in database.
		 */
		new UserModel(new UserID(bebrasID), UserType.INDEPENDENT,
				registerForm.get().fname + " " + registerForm.get().lname, 
				birtyDay, 
				new Date(), 
				passwordHEX,
				saltHEX, registerForm.get().email, 
				Gender.Male, registerForm.get().prefLanguage).save();
		
		return ok(registerLandingPage.render("Succes", new ArrayList<Link>(), bebrasID,passwordHEX));
	}

	public static Result login(){
		setCommonHeaders();
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		//We need to do this check, because a user can this URL without providing POST data.
		if(loginForm.get().id == null && loginForm.get().password == null){
			return ok(login.render("login", 
					new ArrayList<Link>(),
					form(Login.class)
			));
		}else{//POST data is available to us. Try to validate the user.
			return validate_login();
		}
		
	}
	
	public static Result validate_login(){
		setCommonHeaders();
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		//We do the same check here.
		if(loginForm.get().id == null && loginForm.get().password == null){
			return Application.index();
		}else{ //POST data is available to us. Try to validate the user.
			byte[] salt = null; //the users salt saved in the db.
			byte[] passwordByteString = null; //the output from the PBKDF2 function.
			String passwordHEX = null; // The password from the PBKDF2 output converted into a string.
			String passwordDB = null; //the pasword as it is saved in the database.
			
			UserModel userModel = Ebean.find(UserModel.class).where().eq(
					"id",loginForm.get().id).findUnique();
			if(userModel == null){
				return ok(loginLandingPage.render("Failed to login", new ArrayList<Link>(), "Error while logging in: email" + loginForm.get().id));
			}
			passwordDB = userModel.password;
			SecretKeyFactory secretFactory = null;
			try{
			salt = Hex.decodeHex(userModel.hash.toCharArray());
			}catch(Exception e){}
			
			KeySpec PBKDF2 = new PBEKeySpec(loginForm.get().password.toCharArray(), salt, 1000, 160);

			try{
				secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			}catch(Exception e){}


			try {
				passwordByteString = secretFactory.generateSecret(PBKDF2).getEncoded();
			} catch (InvalidKeySpecException e) {}
			try{
				passwordHEX = new String(Hex.encodeHex(passwordByteString));
			}catch(Exception e){}
			
			
			if(passwordHEX.equals(passwordDB)){ 
				//TODO: this should be users landing page based on type of account.
				return ok(loginLandingPage.render("Succes", new ArrayList<Link>(), "Welkom " + userModel.name));
			}else{
				return ok(loginLandingPage.render("Failed to login", new ArrayList<Link>(), passwordHEX + "|" + passwordDB + "|" +
						new String(Hex.encodeHex(salt))));
			}
		}
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
		public String gender;
		public String prefLanguage;
	}
	
	public static class Login{
		public String id;
		public String password;
	}

}
