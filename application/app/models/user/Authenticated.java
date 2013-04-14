package models.user;

import models.dbentities.UserModel;

public abstract class Authenticated extends User{

    public Authenticated(UserModel data, UserType type) {
        super(data, type);
        ROLES.add(Role.CHANGEPASSWORD);
        ROLES.add(Role.LANDINGPAGE);
    }

}
