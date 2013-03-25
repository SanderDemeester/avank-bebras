/**
 * 
 */
package models.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import controllers.user.Type;
import play.data.format.Formats;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * @author Jens N. Rammant
 *
 */
@Entity
@Table(name="users")
public abstract class UserModel extends Model{

	@Id
    public String id;
    public String name;
    
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date birthdate;
    
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    public Date registrationDate;
    public String prefLanguage;
    public String password;
    public String hash;
    public String telephone;
    public String address;
    public String email;
    
    @Enumerated(EnumType.STRING)
    public Gender gender;
    
    @Enumerated(EnumType.STRING)
    public Type type;
    
    public boolean active;
    
    public UserModel(UserID id, Type loginType, String name){
    	this.id = id.getUserID();
    	this.type = loginType; 
    	this.name = name;

    }
    
    /**
     * A finder for User.
     * We will use this finder to execute specific sql query's.
     */
    public static Finder<Integer,UserModel> find = new Model.Finder<Integer, UserModel>(Integer.class,UserModel.class);
    
}
