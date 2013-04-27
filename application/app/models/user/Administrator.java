
package models.user;

import models.dbentities.UserModel;
import play.mvc.Content;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Administrator extends Organizer{

    public Administrator(UserModel data) {
        super(data, UserType.ADMINISTRATOR);
        ROLES.add(Role.MANAGEFAQ); //Give admin the ability to manage FAQ
        ROLES.add(Role.MANAGECONTESTS);
        ROLES.add(Role.DATAMANAGER);
    }

}
