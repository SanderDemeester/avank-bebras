
package datamodels;

import enums.Gender;
import enums.Language;
import java.util.Date;

/**
 *
 * @author Jens N. Rammant
 */
public class User {
    private String id;
    private String name;
    private Gender gender;
    private String email;
    private Language preferredLanguage;
    private Date registrationDate;
    private Date birthDate;
    private boolean active;

    public User(String id, String name, Gender gender, String email, Language preferredLanguage, Date registrationDate, Date birthDate, boolean active) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.preferredLanguage = preferredLanguage;
        this.registrationDate = registrationDate;
        this.birthDate = birthDate;
        this.active = active;
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
