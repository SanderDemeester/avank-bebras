/**
 *
 */
package models.dbentities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import models.user.Gender;
import models.user.UserType;
import models.user.UserID;

import play.data.format.Formats;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * @author Jens N. Rammant
 *
 */
@Entity
@Table(name="users")
public class UserModel extends Model{

	@Id
	public String id;
	public String name;

	@Formats.DateTime(pattern = "yyyy/dd/mm")
	public Date birthdate;

	@Formats.DateTime(pattern = "yyyy/dd/mm")
	public Date registrationdate;
	public String preflanguage;
	public String password;
	public String hash;
	public String telephone;
	public String address;
	public String email;

	@Enumerated(EnumType.STRING)
	public Gender gender;

	@Enumerated(EnumType.STRING)
	public UserType type;

	public boolean active;

	@Column(name="class")
	public String classgroup;

	public UserModel(UserID id, UserType loginType, String name,
			Date birthdate, Date registrationdate,
			String password, String hash, String email,
			Gender gender, String preflanguage){
		
		this.id = id.getUserID();
		this.type = loginType; 
		this.name = name;
		this.birthdate = birthdate;
		this.registrationdate = registrationdate;
		this.password = password;
		this.hash = hash;
		this.email = email;
		this.gender = gender;
		this.preflanguage = preflanguage;
		active = true;

	}

	/**
	 * A finder for User.
	 * We will use this finder to execute specific sql query's.
	 */
	public static Finder<Integer,UserModel> find = new Model.Finder<Integer, UserModel>(Integer.class,UserModel.class);

}
