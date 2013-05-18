package models.user;

import models.dbentities.UserModel;
/**
 * @author Sander Demeester
 */
public abstract class Authenticated extends User{

    /**
     * Constructor
     * @param data Data model class
     * @param userType
     */
    public Authenticated(UserModel data, UserType type) {
        super(data, type);
        ROLES.add(Role.SETTINGS);
        ROLES.add(Role.LANDINGPAGE);
        ROLES.add(Role.VIEWSTATS);
    }

    @Override
    public boolean isAnon() {
        return false;
    }

}
