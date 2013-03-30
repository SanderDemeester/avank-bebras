package models.user.factory;

import models.dbentities.UserModel;
import models.user.User;

public interface UserFactory {
    
    public User create(UserModel userModel);
    
}
