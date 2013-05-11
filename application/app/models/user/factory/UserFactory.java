package models.user.factory;

import models.dbentities.UserModel;
import models.user.User;

/**
 * Make users
 * @author Ruben Taelman
 *
 */
public interface UserFactory {

    public User create(UserModel userModel);

}
