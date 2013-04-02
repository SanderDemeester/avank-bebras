
package models.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import play.mvc.Result;
import controllers.user.Roles;

/**
 * Class to handle UserAuthentication.
 * @author Sander Demeester
 */
public class AuthenticationManager {


    private HashMap<String,User> mappingFromStringToUser = new HashMap<String,User>();
    private HashMap<String,LoginState> mappingFromSessieIDtoLoginState =
        new HashMap<String,LoginState>();


    /**
     * AuthenticationManager constructor.
     */
    public AuthenticationManager(){

    }

    /*
     * returns the landing page of the mimicked user
     * @result Mimicked User Landing Page
     */
    public Result mimicUser(){
        return null;
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
    public LoginState getLoginState(String sessieID){
        return mappingFromSessieIDtoLoginState.get(sessieID);
    }

    /**
     *
     * @return get userObject from sessieID
     */
    public User getUserObject(String sessieID){
        return mappingFromStringToUser.get(sessieID);
    }

}
