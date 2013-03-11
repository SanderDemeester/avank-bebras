
package datamodels;

import enums.Gender;
import enums.Language;
import enums.UserType;
import java.util.Date;

/**
 *
 * @author Jens N. Rammant
 */
public class User implements Model{
    private String id;
    private String name;
    private Gender gender;
    private String email;
    private Language preferredLanguage;
    private Date registrationDate;
    private Date birthDate;
    private boolean active;
    private String password;
    private String hash;
    private UserType type;

    public User(String id, String name, Gender gender, String email, Language preferredLanguage, Date registrationDate, Date birthDate, boolean active, String password, String hash, UserType type) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.preferredLanguage = preferredLanguage;
        this.registrationDate = registrationDate;
        this.birthDate = birthDate;
        this.active = active;
        this.password = password;
        this.hash = hash;
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public String getHash() {
        return hash;
    }

    public UserType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean isActive() {
        return active;
    }
    
    
    
}
