
package datamodels;

import enums.Gender;
import enums.Language;
import enums.UserType;
import java.util.Date;

/**
 *
 * @author Jens N. Rammant
 */
public class Teacher extends User{
    
    private String phoneNumber;
    private String address;

    public Teacher(String phoneNumber, String address, String id, String name, Gender gender, String email, Language preferredLanguage, Date registrationDate, Date birthDate, boolean active, String password, String hash) {
        super(id, name, gender, email, preferredLanguage, registrationDate, birthDate, active, password, hash, UserType.TEACHER);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
    
    
}
