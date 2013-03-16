
package models.user;

import play.mvc.Result;

/**
 * The default abstract class for User.
 * @author Sander Demeester
**/

public abstract class User {

    private UserID id;
    private String loginType;

    public User(){

    }

    /**
     * Returns info about this user as a String.
     * @return Userinfo.
     */
    public String getUserInfo() {
        return null;
    }

    /**
     * Reset the password of this user. Is delegated to the
     * AuthenticationManager.
     */
    public void resetPassword(){

    }

    /**
     * Logs out the user. Is delegated to the AuthenticationManager.
     */
    public void logout(){

    }

    
}
