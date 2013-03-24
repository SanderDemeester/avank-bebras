
package models.user;

import javax.persistence.Entity;

import controllers.user.Type;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */
@Entity
public class Administrator extends Organizer{

    /**
     * Constructor for Administrator.
     **/
    public Administrator(UserID id, Type loginType, String name){
        super(id,loginType,name);
    }

}
