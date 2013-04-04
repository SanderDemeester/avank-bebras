package models.user.factory;

import models.dbentities.UserModel;
import models.user.Author;
import models.user.User;

public class AuthorUserFactory implements UserFactory{

    @Override
    public User create(UserModel userModel) {
        return new Author(userModel);
    }

}
