
package models.user;

import models.dbentities.UserModel;

/**
 * Abstract superUser class
 * @author Sander Demeester
**/

public abstract class SuperUser extends Authenticated{

	/**
     * Constructor
     * @param data Data model class
     * @param type Usertype
     */
    public SuperUser(UserModel data, UserType type) {
        super(data, type);
        ROLES.add(Role.QUESTIONEDITOR);
    }

    /**
     * reset password for user.
     * @param user
     */
    public void resetPassword(User user){
    }
}
