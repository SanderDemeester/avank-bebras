package models.user;

import play.mvc.Content;
import play.mvc.Result;

public class Anon extends User{

    public Anon() {
        super(null, UserType.ANON);
        ROLES.add(Role.REGISTER);
        ROLES.add(Role.LOGIN);
    }

}
