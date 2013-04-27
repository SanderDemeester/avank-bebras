package models.user;

import java.util.Calendar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import models.dbentities.UserModel;
import models.user.Gender;


@RunWith(Suite.class)
public class UserTests {

    public static UserModel createTestUserModel(UserType type) {
        return new UserModel(
                "a",
                type,
                "Test",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime(),
                "Pass",
                "12345",
                "test@user.com",
                Gender.Other,
                "en"
        );
    }

}
