package models.user;

import java.util.Calendar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import models.dbentities.UserModel;
import models.user.Gender;
import models.user.UserID;


@RunWith(Suite.class)
@SuiteClasses({ LoginStateTest.class })
public class UserTests {

    public static UserModel createTestUserModel(UserType type) {
        return new UserModel(
                new UserID("a"),
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
