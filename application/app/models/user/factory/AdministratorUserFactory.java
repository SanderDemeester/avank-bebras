package models.user.factory;

import models.dbentities.UserModel;
import models.user.Administrator;
import models.user.User;

/**
 * Make admin user
 * @author Ruben Taelman
 *
 */
public class AdministratorUserFactory implements UserFactory{

    @Override
    public User create(UserModel userModel) {
        return new Administrator(userModel);
    }

}
