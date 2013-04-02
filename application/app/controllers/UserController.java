package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import models.EMessages;
import models.data.Link;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.User;
import models.user.UserID;
import models.user.UserType;

import org.apache.commons.codec.binary.Hex;

import play.Play;
import play.api.libs.Crypto;
import play.api.templates.Html;
import play.api.templates.Template1;
import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.mvc.Result;
import play.mvc.Results;
import views.html.error;
import views.html.loginLandingPage;
import views.html.register;
import views.html.registerLandingPage;
import views.html.landingPages.AdminLandingPage;
import views.html.landingPages.IndependentPupilLandingPage;
import views.html.landingPages.OrganizerLandingPage;
import views.html.landingPages.PupilLandingPage;

import com.avaje.ebean.Ebean;

/**
 * This class receives all GET requests and based on there session identifier (cookie)
 * and current role in the system they will be served a different view.
 * @author Sander Demeester, Ruben Taelman
 */
public class UserController extends EController{

	/**
	 * This hashmap embodies the mapping from a Type to a view.
	 * Each view is responsible for getting all information from the DataModel and make a
	 * beautiful view for the user :)
	 */
	private static HashMap<UserType, Class<?>> landingPages = new HashMap<UserType, Class<?>>(){{
		put(UserType.ADMINISTRATOR, AdminLandingPage.class);
		put(UserType.INDEPENDENT, IndependentPupilLandingPage.class);
		put(UserType.INDEPENDENT, IndependentPupilLandingPage.class);
		put(UserType.ORGANIZER, OrganizerLandingPage.class);
		put(UserType.PUPIL,PupilLandingPage.class);
	}};

	public UserController(){

	}
	/**
	 * This methode gets requested when the user clicks on "signup".
	 * @author Sander Demeester
	 * @return Result page.
	 */
	public static Result signup(){
		setCommonHeaders();
		List<Link> breadcrumbs = new ArrayList<Link>();
		breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Sign Up", "/signup"));
		return ok(register.render("Registration", 
		          breadcrumbs,
				  form(Register.class)
			   ));
	}

	/**
	 * this methode is called when the user submits his/here register information.
	 * @author Sander Demeester
	 * @return Result page
	 */
	public static Result register(){
		setCommonHeaders();
		
		// Bind play form request.
		Form<Register> registerForm = form(Register.class).bindFromRequest();
		
		if(registerForm.hasErrors()){ // If the form contains error's (specified by "@"-annotation in the class "Register" then this will be true.
			return badRequest(error.render("Fout", new ArrayList<Link>(), form(Register.class), "Invalid request"));
		}
		
		// Setup a secure PRNG
		SecureRandom random = null;
		
		// Init keyFactory to generate a random string using PBKDF2 with SHA1.
		SecretKeyFactory secretFactory = null;
		
		// Resulting password will be in a byte[] array.
		byte[] passwordByteString = null;
		
		// We will save the password in HEX-format in the database;
		String passwordHEX = "";
		
		// Same for salt
		String saltHEX = "";
		Date birtyDay = new Date();
		
		// The first 2 letters of fname and the 7 letters from lname make the bebrasID.
		String bebrasID = null; 

		// Get instance of secureRandom.
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {}
		
		byte[] salt = new byte[16]; //RSA PKCS5

		// Get salt
		random.nextBytes(salt);

		// Get the key for PBKDF2.
		KeySpec PBKDF2 = new PBEKeySpec(registerForm.get().password.toCharArray(), salt, 1000, 160);

		// init keyFactory.
		try{
			secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		}catch(Exception e){}

		// Generate password from PBKDF2.
		try {
			passwordByteString = secretFactory.generateSecret(PBKDF2).getEncoded();
		} catch (InvalidKeySpecException e) {}
		try{ // Encocde our byte arrays to HEX dumps (to save in the database).
			saltHEX = new String(Hex.encodeHex(salt));
			passwordHEX = new String(Hex.encodeHex(passwordByteString));
			birtyDay = new SimpleDateFormat("yyyy/dd/mm").parse(registerForm.get().bday);
		}catch(Exception e){}

		// TODO: Add support for names with only one character
		// Generate bebrasID.
		bebrasID = registerForm.get().fname.toLowerCase().substring(0,2);
		bebrasID += registerForm.get().lname.toLowerCase().substring(0, registerForm.get().lname.length() < 7 ? registerForm.get().lname.length() : 7);
		
		// Construct welcome message.
		String r = "Welkom ";
		r += registerForm.get().fname + "!";

		// Check if the email adres is uniqe.
		if(!registerForm.get().email.isEmpty()){

			if(Ebean.find(UserModel.class).where().eq(
					"email",registerForm.get().email).findUnique() != null){
				return badRequest(error.render("Fout",new ArrayList<Link>(),form(Register.class),"Er bestaat al een gebruiker met het gekozen email address"));
			}
		}

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

		return ok(registerLandingPage.render("Succes", new ArrayList<Link>(), bebrasID));
	}
	/**
	 * This methode is called when the users clicks on "login", the purpose of this code is to validate the users login credentials.
	 * @author Sander Demeester
	 * @return returns the loginLandingPage succes, this landing page should redirect to /home
	 */
	public static Result validate_login(){
		setCommonHeaders();
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		
		// We do the same check here, if the input forms are empty return a error message.
		if(loginForm.get().id == null && loginForm.get().password == null){
			return Application.index();
		}else{ //POST data is available to us. Try to validate the user.
			// For storing the users salt form the database.
			byte[] salt = null; 
			
			// For storing the output of the PBKDF2 function.
			byte[] passwordByteString = null; 
			
			// To store the output from the PBKDF2 function in HEX.
			String passwordHEX = null; 
			
			// To store the password as it is stored in the database.
			String passwordDB = null; 

			// Get the users information from the database.
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
				String cookie = "";
				try {
					//generate random id to auth user.
					cookie = Integer.toString(Math.abs(SecureRandom.getInstance("SHA1PRNG").nextInt(100)));

					//set the cookie. There really is no need for Crypto.sign because a cookie should be random value that has no meaning
					response().setCookie(AuthenticationManager.COOKIENAME, Crypto.sign(cookie));

					//authenticate the user to the AuthenticationManager
					AuthenticationManager.getInstance().login(userModel);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ok(loginLandingPage.render("Succes", new ArrayList<Link>(), "Welkom " + userModel.name));
			}else{
				return badRequest(error.render("Failed to login", new ArrayList<Link>(), form(Register.class), "Invalid login"));
			}
		}
	}

	public static Result logout(){
	    AuthenticationManager.getInstance().logout();
	    return Results.redirect(routes.Application.index());
	}

	/**
	 * @author Sander Demeester
	 * @return Returns a scala template based on the type of user that is requesting the page.
	 **/
	@SuppressWarnings("unchecked")
	public static Result landingPage() throws Exception{
		UserType type = UserType.INDEPENDENT;
		Class<?> object = Play.application().classloader().loadClass("views.html.landingPages." + landingPages.get(type).getSimpleName() + "$");
		Template1<User, Html> viewTemplate = (Template1<User, Html>)object.getField("MODULE$").get(null);
		return ok(viewTemplate.render(AuthenticationManager.getInstance().getUser()));
	}

	/**
	 * Inline class that contains public fields for play forms. 
	 * @author Sander Demeester
	 */
	public static class Register{
		@Required
		public String fname;
		@Required
		public String lname;
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
	/**
	 * Inline class that contains public fields for play forms.
	 * @author Sander Demeester
	 */
	public static class Login{
		public String id;
		public String password;
	}

}
