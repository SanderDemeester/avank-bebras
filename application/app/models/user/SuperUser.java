
package models.user;

import models.dbentities.UserModel;

/**
 * Abstract superUser class
 * @author Sander Demeester
**/

public abstract class SuperUser extends Authenticated{

    static {
        ROLES.add(Role.QUESTIONEDITOR);
    }

    public SuperUser(UserModel data, UserType type) {
		super(data, type);
	}

	/**
     * reset password for user.
     * @param user
     */
    public void resetPassword(User user){

    }
}
