package models.user;

import models.dbentities.UserModel;

public class Author extends Authenticated{

    public Author(UserModel data) {
        super(data, UserType.AUTHOR);
        ROLES.add(Role.QUESTIONEDITOR);
    }

}
