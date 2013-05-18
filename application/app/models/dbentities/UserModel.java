package models.dbentities;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import models.data.Language;
import models.management.Editable;

import com.avaje.ebean.validation.NotNull;

import controllers.util.DateFormatter;

import models.management.Listable;
import models.management.ManageableModel;
import models.user.Gender;
import models.user.GenderWrap;
import models.user.UserType;
import models.user.UserTypeWrap;
import play.data.format.Formats;
import play.db.ebean.Model;
import models.EMessages;

/**
 * @author Jens N. Rammant
 * @author Thomas Mortier
 * Class for the db entity for a user
 */
@Entity
@Table(name="users")
public class UserModel extends ManageableModel implements Listable{

    private static final long serialVersionUID = 1L;

    /**
     * ID of the user
     */
    @Id
    @NotNull
    @JoinColumn(name="id")
    public String id;

    /**
     * Type of the user
     */
    @Enumerated(EnumType.STRING)
    @ManyToOne
    @NotNull
    @JoinColumn(name="type")
    public UserType type;

    /**
     * Wrapper for the type
     */
    @Transient
    @Editable
    public UserTypeWrap wrap_type;

    /**
     * Name of the user
     */
    @Editable
    @NotNull
    @JoinColumn(name="name")
    public String name;

    /**
     * Name of the email
     */
    @Editable
    @JoinColumn(name="email")
    public String email;

    /**
     * Gender of the user
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @ManyToOne
    @JoinColumn(name="gender")
    public Gender gender;

    /**
     * Wrapper for the gender
     */
    @Transient
    @Editable
    public GenderWrap wrap_gender;

    /**
     * Birthdate of the user
     */
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    @Editable
    @ManyToOne
    @NotNull
    @JoinColumn(name="bday")
    public Date birthdate;

    /**
     * Registrationdate of the user
     */
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    @Editable
    @ManyToOne
    @NotNull
    @JoinColumn(name="regdate")
    public Date registrationdate;

    /**
     * Preferred language of the user
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name="language")
    public String preflanguage;

    /**
     * Wrapper for preflanguage
     */
    @Editable
    @Transient
    public Language wrap_language;

    /**
     * Comment about the user
     */
    @Editable
    @JoinColumn(name="comment")
    public String comment;

    /**
     * Date until when the user is blocked
     */
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    @Editable
    @ManyToOne
    @JoinColumn(name="blockeduntil")
    public Date blockeduntil;

    /**
     * Is the user blocked
     */
    @Editable
    @Transient
    public boolean blocked;

    /**
     * Password of the user (hashed)
     */
    public String password;
    /**
     * Salt used for the hashing of the password
     */
    public String hash;
    /**
     * Telephone number of the user
     */
    public String telephone;
    /**
     * Address of the user
     */
    public String address;
    /**
     * Token used for password resetting
     */
    public String reset_token;

    /**
     * ID of the class the user is currently in as pupil
     */
    @Override
    public String getID() {
        return id;
    }

    @Column(name="class")
    public Integer classgroup;

    /**
     * Constructor
     * @param id ID
     * @param loginType type of the user
     * @param name Name
     * @param birthdate Birthdate
     * @param registrationdate Registrationdate
     * @param password password (hashed)
     * @param hash salt
     * @param email email
     * @param gender gender
     * @param preflanguage preferred language
     */
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
        this.blockeduntil = null;
        EMessages.setLang(preflanguage);

    }

    /**
     * Constructor
     */
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
        List<UserModel> users = find.all();
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
                EMessages.get("languages." + preflanguage),
                comment,
                DateFormatter.formatDate(this.blockeduntil)
        };
        return res;
    }

    /**
     * @return String representation of birthdate
     */
    public String getBirthDate(){
        return convertDate(this.birthdate);
    }

    private String convertDate(Date d){
        return DateFormatter.formatDate(d);
    }

    /**
     *
     * @return whether the user is currently blocked
     */
    public boolean isCurrentlyBlocked(){
        if(this.blockeduntil==null) return false;
        Date today = Calendar.getInstance().getTime();
        return !today.after(this.blockeduntil);
    }
}
