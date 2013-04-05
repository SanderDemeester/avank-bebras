package models.user.factory;

import models.dbentities.UserModel;
import models.user.Pupil;
import models.user.User;
public class PupilUserFactory implements UserFactory{

    @Override
    public User create(UserModel userModel) {
        return new Pupil(userModel);
    }

}
