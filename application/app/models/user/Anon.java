package models.user;

/**
 * @author Sander Demeester
 */
public class Anon extends User{

    /**
     * Constructor
     */

    public Anon() {
        super(null, UserType.ANON);
        ROLES.add(Role.REGISTER);
        ROLES.add(Role.LOGIN);
    }

    @Override
    public boolean isAnon() {
        return true;
    }

}
