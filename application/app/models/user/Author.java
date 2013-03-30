package models.user;

import models.dbentities.UserModel;

public class Author extends Authenticated{
    
    static {
        ROLES.add(Role.QUESTIONEDITOR);
    }
    
    public Author(UserModel data) {
        super(data, UserType.AUTHOR);
    }

}
