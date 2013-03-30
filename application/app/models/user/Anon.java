package models.user;

import models.dbentities.UserModel;

public class Anon extends User{
    
    static {
        ROLES.add(Role.REGISTER);
        ROLES.add(Role.LOGIN);
    }

    public Anon() {
        super(null, UserType.ANON);
        // TODO Auto-generated constructor stub
    }

}
