
package models.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import models.dbentities.UserModel;
import models.user.factory.AdministratorUserFactory;
import models.user.factory.AuthorUserFactory;
import models.user.factory.IndependentUserFactory;
import models.user.factory.OrganizerUserFactory;
import models.user.factory.PupilUserFactory;
import models.user.factory.TeacherUserFactory;
import models.user.factory.UserFactory;
import play.mvc.Http.Context;
import controllers.user.Roles;
import controllers.user.Type;

/**
 * Class to handle UserAuthentication.
 * @author Sander Demeester
 * @author Ruben Taelman
 */
public class AuthenticationManager {
    private static AuthenticationManager _instance = null;
	
	//private Map<String,User> sessionIdToUser = new HashMap<String,User>();
	//private Map<String,LoginState> mappingFromSessieIDtoLoginState =	new HashMap<String,LoginState>();
	private Map<String, Stack<User>> users;
	private static final String COOKIENAME = "avank.auth";
	private static final Map<Type, UserFactory> FACTORIES = new HashMap<Type, UserFactory>();
	
	static {
	    FACTORIES.put(Type.ADMINISTRATOR, new AdministratorUserFactory());
	    FACTORIES.put(Type.AUTHOR, new AuthorUserFactory());
	    FACTORIES.put(Type.INDEPENDENT, new IndependentUserFactory());
	    FACTORIES.put(Type.ORGANIZER, new OrganizerUserFactory());
	    FACTORIES.put(Type.PUPIL, new PupilUserFactory());
	    FACTORIES.put(Type.TEACHER, new TeacherUserFactory());
	}

    /**
     * AuthenticationManager constructor.
     */
    private AuthenticationManager(){
        users = new HashMap<String, Stack<User>>();
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
    public List<Roles> getUserRolles(User user){
    	return new ArrayList<Roles>();
    }
    
    /**
     * get the loginState of the sessieID
     * @return getLoginState 
     */
    /*public LoginState getLoginState(String sessieID){
    	return mappingFromSessieIDtoLoginState.get(sessieID);
    }*/
    
    /**
     * 
     * @return get userObject from sessieID
     */
    /*public User getUserObject(String sessieID){
    	return mappingFromStringToUser.get(sessieID);
    }*/
    
    public void login(UserModel userModel) {
        User user = create(userModel);
        Stack<User> stack = users.get(getAuthCookie());
        stack.add(user);
    }
    
    private User create(UserModel userModel) {
        return FACTORIES.get(userModel.type).create(userModel);
    }
    
    /**
     * Get the current authenticated user object
     * @return
     */
    public User getUser() {
        Stack<User> stack = users.get(getAuthCookie());
        if(stack==null) return null;
        else            return stack.firstElement();
    }
    
    private String getAuthCookie() {
        return Context.current().session().get(COOKIENAME);
    }

}
