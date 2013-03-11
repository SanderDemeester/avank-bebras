
package datamodels;

import enums.Gender;
import enums.Language;
import enums.UserType;
import java.util.Date;

/**
 *
 * @author Jens N. Rammant
 */
public class Organizer extends User {

    public Organizer(String id, String name, Gender gender, String email, Language preferredLanguage, Date registrationDate, Date birthDate, boolean active, String password, String hash) {
        super(id, name, gender, email, preferredLanguage, registrationDate, birthDate, active, password, hash, UserType.ORGANIZER);
    }

    

    
    

}
