package models.user;

import models.dbentities.UserModel;

public abstract class Authenticated extends User{

    static {
        ROLES.add(Role.CHANGEPASSWORD);
        ROLES.add(Role.LANDINGPAGE);

        // TODO: Remove this once question authors can be created!!!!!!!
        ROLES.add(Role.QUESTIONEDITOR);
    }

    public Authenticated(UserModel data, UserType type) {
        super(data, type);
    }

}