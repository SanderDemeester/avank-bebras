package models.user;

import play.mvc.Content;
import play.mvc.Result;
import models.dbentities.UserModel;

public class Author extends Authenticated{

    public Author(UserModel data) {
        super(data, UserType.AUTHOR);
        ROLES.add(Role.QUESTIONEDITOR);
    }

}
