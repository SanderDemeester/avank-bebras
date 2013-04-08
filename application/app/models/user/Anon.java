package models.user;

import play.mvc.Content;
import play.mvc.Result;

public class Anon extends User{
    
    static {
        ROLES.add(Role.REGISTER);
        ROLES.add(Role.LOGIN);
    }

    public Anon() {
        super(null, UserType.ANON);
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
