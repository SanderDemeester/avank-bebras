
package models.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import models.EMessages;
import models.dbentities.UserModel;
import models.user.factory.AdministratorUserFactory;
import models.user.factory.AuthorUserFactory;
import models.user.factory.IndependentUserFactory;
import models.user.factory.OrganizerUserFactory;
import models.user.factory.PupilUserFactory;
import models.user.factory.TeacherUserFactory;
import models.user.factory.UserFactory;

import org.apache.commons.codec.binary.Hex;

import play.data.Form;
import play.mvc.Http.Context;
import play.mvc.Http.Cookie;
import scala.collection.mutable.HashSet;

import com.avaje.ebean.Ebean;

import controllers.UserController.Register;
import controllers.util.PasswordHasher;
import controllers.util.PasswordHasher.SaltAndPassword;

import models.user.IDGenerator;

/**
 * Class to handle UserAuthentication.
 * @author Sander Demeester
 * @author Ruben Taelman
 */
public class AuthenticationManager {
	private static AuthenticationManager _instance = null;

	//private Map<String,User> sessionIdToUser = new HashMap<String,User>();
	//private Map<String,LoginState> mappingFromSessieIDtoLoginState =    new HashMap<String,LoginState>();

	// String: value of the COOKIENAME cookie
	private Map<String, Stack<User>> users;
	
	private HashSet<String> loggedInUserID;
	public static final String COOKIENAME = "avank.auth";
	private static final Map<UserType, UserFactory> FACTORIES = new HashMap<UserType, UserFactory>();
	
	public static final int INVALID_LOGIN = 0;
	public static final int VALID_LOGING = 1;
	public static final int DUPLICATED_LOGIN = 2;

	static {
		FACTORIES.put(UserType.ADMINISTRATOR, new AdministratorUserFactory());
		FACTORIES.put(UserType.AUTHOR, new AuthorUserFactory());
		FACTORIES.put(UserType.INDEPENDENT, new IndependentUserFactory());
		FACTORIES.put(UserType.ORGANIZER, new OrganizerUserFactory());
		FACTORIES.put(UserType.PUPIL, new PupilUserFactory());
		FACTORIES.put(UserType.TEACHER, new TeacherUserFactory());
	}

	/**
	 * AuthenticationManager constructor.
	 */
	private AuthenticationManager(){
		users = new HashMap<String, Stack<User>>();
		loggedInUserID = new HashSet<String>();
	}

	public static AuthenticationManager getInstance() {
		if(_instance==null)
			_instance = new AuthenticationManager();
		return _instance;
	}

	/*
	 * resets the password of the user identified with userID, assuming the email-
	 * address is correct
	 */
	public void resetPassword(String userID,String emailaddress){

	}

	/**
	 * @return Gives back a list of the roles that a given user has.
	 **/
	public List<Role> getUserRolles(User user){
		return new ArrayList<Role>();
	}

	/**
	 *
	 * @return get userObject from sessieID
	 */
	/*public User getUserObject(String sessieID){
        return mappingFromStringToUser.get(sessieID);
    }*/

	/**
	 * Login or mimic with a new usermodel
	 * @param userModel
	 * @return The logged in user. This methode can also return null. If the context is logging in, then null means that there is a duplicated login.
	 * In the context of mimicking. Then it means that policy does not allow to mimic this user.
	 */
	public User login(UserModel userModel, String cookie) {
		// TODO: kick users when they are logged in from somewhere else, unless a superuser is mimicking them

		// Check if the current user can mimic that user and login (add to stack) if that's the case
		User current = getUser();
		User user = create(userModel);
		Stack<User> stack = users.get(cookie);
		
		if(loggedInUserID.contains(user.getID()) && !current.isMimicking()) return null;
		loggedInUserID.add(user.getID());
		if(stack == null) { // The user is not yet logged in (would be the case if the stack is empty)
			stack = new Stack<User>();
			stack.push(user);
			users.put(cookie, stack);
		} else if(current.canMimic(user)) { // If the current user can mimic the other user.
			stack.push(user);
		}else{
			System.out.println("will return null");
			return null;
		}

		EMessages.setLang(userModel.preflanguage);

		if(stack.size() == 0)
			return user;
		else
			return stack.firstElement();
	}

	/**
	 * Logout a usermodel (or pop a mimic)
	 */
	public User logout() {
		Stack<User> stack = users.get(getAuthCookie());
		loggedInUserID.remove(stack.peek().getID());
		stack.pop();
		if(stack.isEmpty()) {
			users.put(getAuthCookie(), null);
			return null;
		} else {
			stack.peek().setMimickStatus(false);
			return stack.peek();
		}
	}

	private User create(UserModel userModel) {
		return FACTORIES.get(userModel.type).create(userModel);
	}

	/**
	 * Get the current authenticated user object.
	 * @return the current authenticated user object.
	 */
	public User getUser() {
		Stack<User> stack = users.get(getAuthCookie());
		if(stack==null) return new Anon();
		else
			return stack.firstElement();
	}

	public User getCurrentMimickUser(){
		Stack<User> stack = users.get(getAuthCookie());
		return stack.peek();
	}

	private String getAuthCookie() {
		Cookie cookie = Context.current().request().cookies().get(COOKIENAME);
		if(cookie == null)
			return null;
		return cookie.value();
	}

	public boolean isLoggedIn() {
		return !this.getUser().getType().equals(UserType.ANON);
	}

	/**
	 * 
	 * @param bebrasID.
	 * @return true is there is a user logged in with provided id.
	 */
	public boolean isUserLoggedIn(String id){
		return loggedInUserID.contains(id);
	}

	/**
	 * Check if this current users is mimicking somebody.
	 * @return return true is the current user is mimicking an other user.
	 */
	public boolean mimicking(){
		return getUser().isMimicking();
	}

	/**
	 * @param Password string.
	 * @return The strengthed password as preformed by javascript code on client machine.
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public String simulateClientsidePasswordStrengthening(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] salt = sha256.digest(password.getBytes());
		// Get the key for PBKDF2.
		KeySpec PBKDF2 = new PBEKeySpec(password.toCharArray(), salt, 10, 128);
		SecretKeyFactory secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		String k = new String(Hex.encodeHex(secretFactory.generateSecret(PBKDF2).getEncoded()));
		return k;
	}

	/**
	 * Create user.
	 * @param registerForm
	 * @return bebrasID
	 * @throws Exception
	 */
	public String createUser(Form<Register> registerForm) throws Exception{

		Date birtyDay = new Date();



		SaltAndPassword sp = PasswordHasher.generateSP(registerForm.get().password.toCharArray());
		String passwordHEX = sp.password;
		String saltHEX = sp.salt;

		// The first 2 letters of fname and the 7 letters from lname make the bebrasID.
		String bebrasID = null;
		birtyDay = new SimpleDateFormat("dd/MM/yyyy").parse(registerForm.get().bday);


		String name = registerForm.get().name;
		Calendar birthday = Calendar.getInstance();
		birthday.setTime(birtyDay);
		bebrasID = IDGenerator.generate(registerForm.get().name, birthday);
		new UserModel(bebrasID, UserType.INDEPENDENT,
				name,
				birtyDay,
				new Date(),
				passwordHEX,
				saltHEX, registerForm.get().email,
				Gender.valueOf(registerForm.get().gender), registerForm.get().prefLanguage).save();

		return bebrasID;
	}

	/**
	 * the purpose of this code is to validate the users login credentials.
	 * @param id
	 * @param pw
	 * @return true if credentials are ok else false.
	 * @throws Exception
	 */
	public int validate_credentials(String id, String pw, String cookie) throws Exception{
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
				"id",id).findUnique();

		if(userModel == null){
			return INVALID_LOGIN;
		}
		passwordDB = userModel.password;
		SecretKeyFactory secretFactory = null;
		try{
			salt = Hex.decodeHex(userModel.hash.toCharArray());
		}catch(Exception e){}

		KeySpec PBKDF2 = new PBEKeySpec(pw.toCharArray(), salt, 1000, 160);

		try{
			// TODO: waarom niet de secret van Play zelf?
			secretFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		}catch(Exception e){
			//            throw new Exception(EMessages.get("error.text"));
			throw new Exception("ssmldkjfmsqldfjk");
		}

		try {
			passwordByteString = secretFactory.generateSecret(PBKDF2).getEncoded();
		}catch (InvalidKeySpecException e) {
			throw new Exception(EMessages.get("error.text"));
		}
		try{
			passwordHEX = new String(Hex.encodeHex(passwordByteString));
		}catch(Exception e){
			throw new Exception(EMessages.get("error.text"));
		}

		if(passwordHEX.equals(passwordDB)){
			// authenticate user.
			User user = login(userModel, cookie);
			
			// Duplicated login.
			if(user == null) return DUPLICATED_LOGIN;
			return VALID_LOGING;
		}else{
			return INVALID_LOGIN;
		}

	}
}
