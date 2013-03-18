
package models.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import controllers.user.Type;

import play.db.ebean.Model;
import play.mvc.Result;

/**
 * The default abstract class for User.
 * @author Sander Demeester
**/

@Entity
@Table(name="Users")
public abstract class User extends Model{

    private UserID id;
    private Type loginType;
    private String name;
    
    public User(UserID id, Type loginType, String name){
    	this.id = id;
    	this.loginType = loginType; 
    	this.name = name;

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
