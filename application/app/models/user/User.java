
package models.user;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import play.mvc.Content;
import play.mvc.Result;

import models.dbentities.UserModel;

/**
 * The default abstract class for User.
 * @author Sander Demeester
 * @author Ruben Taelman
 * @author Felix Van der Jeugt
 **/

public abstract class User{

    public UserModel data;
    // The roles this user has or can have
    protected Set<Role> ROLES = new HashSet<Role>();// Can be made non-static if roles have to be altered on runtime
    private UserType type;
    
    // Field to check if the users is mimicking some other user;
    private boolean isMimicking = false;

    /**
     * @param data
     */
    public User(UserModel data, UserType type) {
        this.data = data;
        this.type = type;
    }

    /**
    public UserModel data;
     * @param data
     */
    public User(UserModel data) {
        super();
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
     * Logs out the user or pops a mimic.
     */
    public void logout(){
        AuthenticationManager.getInstance().logout();
    }

    /*
     * Returns the landing page. Is to be implemented by the child classes
     *@return Landing Page
     */
    public abstract Content getLandingPage();

    /*
     * Returns the userID
     * @return userID
     */
    public String getID(){
        return data.id;
    }

    /**
     * Check if a user has a certain role
     * @param role
     * @return
     */
    public boolean hasRole(Role role) {
        return ROLES.contains(role);
    }

    /**
     * Returns all the roles this user has.
     * @return This user's roles.
     */
    public List<Role> roles() {
        return new ArrayList<Role>(ROLES);
    }

    /**
     * Get the type of this user
     * @return
     */
    public UserType getType() {
        return this.type;
    }

    /**
     * Check if this user is able to mimic the given user
     * @param user
     * @return
     */
    public boolean canMimic(User user) {
        return this.getType().ordinal() > user.getType().ordinal()
                && this.hasRole(Role.MIMIC);
    }
    
    public boolean isMimicking(){
    	return isMimicking;
    }
    
    public void setMimickStatus(boolean isMimicking){
    	isMimicking = true;
    }

    /*
     * Returns the statistics page
     * @return Statistics Page
     */
    public abstract Result showStatistics();

    /*
     * Returns the personal info page
     * @return Personal Info Page
     */
    public Result showPersonalInformation(){
        return null;
    }

}
