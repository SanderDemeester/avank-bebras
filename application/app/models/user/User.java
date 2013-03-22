
package models.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import com.avaje.ebean.annotation.Formula;
import controllers.user.Type;
import play.db.ebean.Model;
import play.mvc.Result;
import play.data.format.Formats;

/**
 * The default abstract class for User.
 * @author Sander Demeester
**/

@Entity
@Table(name="Users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class User extends Model{

	@Id
    public String id;
    public String name;
    
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date birtyDate;
    
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date registrationDate;
    public String prefLanguage;
    public String password;
    public String hash;
    public String telephone;
    public String address;
    
    @Enumerated(EnumType.STRING)
    public Gender gender;
    
    @Enumerated(EnumType.STRING)
    public Type loginType;
    
    public boolean active;
    
    public User(UserID id, Type loginType, String name){
    	this.id = id.geUserID();
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
