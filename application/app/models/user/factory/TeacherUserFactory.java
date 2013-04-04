package models.user.factory;

import models.dbentities.UserModel;
import models.user.Teacher;
import models.user.User;
public class TeacherUserFactory implements UserFactory{

    @Override
    public User create(UserModel userModel) {
        return new Teacher(userModel);
    }

}
