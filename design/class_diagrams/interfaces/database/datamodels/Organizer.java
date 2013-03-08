
package datamodels;

import enums.Gender;
import enums.Language;
import java.util.Date;

/**
 *
 * @author Jens N. Rammant
 */
public class Organizer extends User {

    public Organizer(String id, String name, Gender gender, String email, Language preferredLanguage, Date registrationDate, Date birthDate) {
        super(id, name, gender, email, preferredLanguage, registrationDate, birthDate);
    }
    

}
