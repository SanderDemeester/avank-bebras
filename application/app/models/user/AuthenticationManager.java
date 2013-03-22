
package models.user;

import play.mvc.Result;

/**
 * Class to handle UserAuthentication.
 * @author Sander Demeester
 */
public class AuthenticationManager {

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

}
