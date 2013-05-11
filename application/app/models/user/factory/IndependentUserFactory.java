package models.user.factory;

import models.dbentities.UserModel;
import models.user.Independent;
import models.user.User;

/**
 * Make independent users
 * @author Ruben Taelman
 *
 */
public class IndependentUserFactory implements UserFactory{

    @Override
    public User create(UserModel userModel) {
        return new Independent(userModel);
    }

}
