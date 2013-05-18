
package models.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    protected Set<Role> ROLES = new TreeSet<Role>();// Can be made non-static if roles have to be altered on runtime
    private UserType type;

    // Field to check if the users is mimicking some other user;
    private boolean isMimicking = false;

    private boolean isMimicTarget = false;

    /**
     * Constructor
     * @param data Data model class
     * @param type User type
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
     * Logs out the user or pops a mimic.
     */
    public void logout(){
        AuthenticationManager.getInstance().logout();
    }

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
     * @return if this user has the given role
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
     * @return the type of the user
     */
    public UserType getType() {
        return this.type;
    }

    /**
     * Check if this user is able to mimic the given user
     * @param user
     * @return if the given user can be mimicked by this user
     */
    public boolean canMimic(User user) {
        return this.getType().ordinal() < user.getType().ordinal()
                && this.hasRole(Role.MIMIC);
    }

    public boolean isMimicking(){
        return isMimicking;
    }

    public boolean isMimicTarget(){
        return isMimicTarget;
    }

    public void setMimicTarget(boolean isMimicTarget){
        this.isMimicking = isMimicTarget;
    }

    public void setMimickStatus(boolean isMimicking){
        this.isMimicking = isMimicking;
    }

    public abstract boolean isAnon();

}
