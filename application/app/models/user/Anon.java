package models.user;


public class Anon extends User{

    public Anon() {
        super(null, UserType.ANON);
        ROLES.add(Role.REGISTER);
        ROLES.add(Role.LOGIN);
    }

}
