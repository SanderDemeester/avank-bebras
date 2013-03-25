
package models.user;

/**
 * Abstract superUser class
 * @author Sander Demeester
**/

public abstract class SuperUser extends User{

    

    public SuperUser(UserModel data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	/**
     * reset password for user.
     * @param user
     */
    public void resetPassword(User user){

    }
}
