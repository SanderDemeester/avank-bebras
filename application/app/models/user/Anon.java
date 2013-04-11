package models.user;

import models.dbentities.UserModel;
import play.mvc.Content;
import play.mvc.Result;

public class Anon extends User{

    public Anon() {
        super(null, UserType.ANON);
        ROLES.add(Role.REGISTER);
        ROLES.add(Role.LOGIN);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Content getLandingPage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result showStatistics() {
        // TODO Auto-generated method stub
        return null;
    }

}
