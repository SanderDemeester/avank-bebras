
package models.user;

import java.util.HashSet;
import java.util.Set;

import models.dbentities.UserModel;

/**
 * The default abstract class for User.
 * @author Sander Demeester, Ruben Taelman
**/

public abstract class User{

	public UserModel data;
	public static Set<Role> ROLES = new HashSet<Role>();// Can be made non-static if roles have to be altered on runtime

    /**
	 * @param data
	 */
	public User(UserModel data) {
		this.data = data;
	}

	/**
     * Returns info about this user as a String.
     * @return Userinfo.
     */
	
	
	
    public String getUserInfo() {
        return null;
    }

    public UserModel getData() {
		return data;
	}

	public void setData(UserModel data) {
		this.data = data;
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
    
    /*
     * Returns the landing page. Is to be implemented by the child classes
     *@return Landing Page
     */
    //public abstract Content getLandingPage();
    
    /*
     * Returns the userID
     * @return userID
     */
    public UserID getID(){
    	//TODO
    	return null;
    }
    
    public boolean hasRole(Role role) {
        return ROLES.contains(role);
    }
    


}
