
package models.user;

import models.dbentities.UserModel;

/**
 * @author Sander Demeester
 */
public class Administrator extends Organizer{

	/**
     * Constructor
     * @param data Data model class
     */
    public Administrator(UserModel data) {
        super(data, UserType.ADMINISTRATOR);
        ROLES.add(Role.MANAGEFAQ); //Give admin the ability to manage FAQ
        ROLES.add(Role.MANAGECONTESTS);
        ROLES.add(Role.DATAMANAGER);
    ROLES.add(Role.MANAGEUSERS);
        ROLES.add(Role.MIMIC);
    }

}
