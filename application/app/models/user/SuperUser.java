
package models.user;

import controllers.user.Type;

/**
 * Abstract superUser class
 * @author Sander Demeester
**/

public abstract class SuperUser extends User{

    public SuperUser(UserID id, Type loginType, String name){
    	super(id,loginType,name);
    }

    /**
     * reset password for user.
     * @param user
     */
    public void resetPassword(User user){

    }
}
