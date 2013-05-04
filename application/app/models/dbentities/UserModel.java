package models.dbentities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import models.data.Language;
import models.management.Editable;

import com.avaje.ebean.validation.NotNull;

import controllers.util.DateFormatter;

import models.management.Listable;
import models.management.ManageableModel;
import models.user.AuthenticationManager;
import models.user.Gender;
import models.user.GenderWrap;
import models.user.UserType;
import models.user.UserTypeWrap;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import models.EMessages;

/**
 * @author Jens N. Rammant
 * @author Thomas Mortier
 * TODO check the date formats
 */
@Entity
@Table(name="users")
public class UserModel extends ManageableModel implements Listable{

    private static final long serialVersionUID = 1L;

    @Id
	@NotNull
	@JoinColumn(name="id")
    public String id;
    
    @Enumerated(EnumType.STRING)
    @ManyToOne
    @NotNull
	@JoinColumn(name="type")
    public UserType type;
    
    @Transient
    @Editable
    public UserTypeWrap wrap_type;
    
    @Editable
    @NotNull
    @JoinColumn(name="name")
    public String name;
    
    @Editable
    @NotNull
    @JoinColumn(name="email")
    public String email;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    @ManyToOne
    @JoinColumn(name="gender")
    public Gender gender;
    
    @Transient
    @Editable
    public GenderWrap wrap_gender;
    
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    @Editable
	@ManyToOne
	@NotNull
	@JoinColumn(name="bday")
    public Date birthdate;
    
    @Formats.DateTime(pattern = "MM/dd/yyyy")
    @Editable
    @ManyToOne
    @NotNull
    @JoinColumn(name="regdate")
    public Date registrationdate;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name="language")
    public String preflanguage;
    
    @Editable
    @Transient
    public Language wrap_language;
    
    @Editable
    @NotNull
    @Constraints.Required
    @JoinColumn(name="active")
    public boolean active;

    public String password;
    public String hash;
    public String telephone;
    public String address;

    @Override
	public String getID() {
		return id;
	}    
    
    @Column(name="class")
    public Integer classgroup;

    public UserModel(String id, UserType loginType, String name,
            Date birthdate, Date registrationdate,
            String password, String hash, String email,
            Gender gender, String preflanguage){

        this.id = id;
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
        EMessages.setLang(preflanguage);
    }

    public UserModel() {
		//empty constructor
	}

	/**
     * A finder for User.
     * We will use this finder to execute specific sql query's.
     */
    public static Finder<String,UserModel> find = new Model.Finder<String, UserModel>(String.class,UserModel.class);



    @Override
    public Map<String, String> options() {
        List<UserModel> users = find.all(); //TODO try-catch
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(UserModel user: users) {
            options.put(user.id, user.id);
        }
        return options;
    }

	@Override
	public String[] getFieldValues() {
		String[] res = {
				id,
				EMessages.get("user." + type.toString()),
				name,
				email,
				EMessages.get("user." + gender.toString()),
				convertDate(birthdate),
				convertDate(registrationdate),
				preflanguage,
				Boolean.toString(active)
		};		
		return res;
	}
	
	public String getBirthDate(){
		return convertDate(this.birthdate);
	}
	
	private String convertDate(Date d){
		return DateFormatter.formatDate(d);
	}
}
