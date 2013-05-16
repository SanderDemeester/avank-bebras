package models.user;

import java.util.HashMap;
import java.util.Map;

import models.dbentities.UserModel;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.status;
import static org.fest.assertions.Assertions.assertThat;
import play.mvc.Result;
import test.ContextTest;

public class ResetPasswordTest extends ContextTest{

    @Test
    public void resetPasswordTest(){
        //fase 1

        Map<String,String> map = new HashMap<String,String>();

        map.put("id","wrong_id");

        Result result = callAction(
                controllers.user.routes.ref.ResetPasswordController.forgotPwdSendMail(),
                fakeRequest().withFormUrlEncodedBody(map)
                );
        assertThat(status(result)).isEqualTo(400);
        assertThat(contentAsString(result)).contains("Invalid ID");

        Map<String,String> map1 = new HashMap<String,String>();

        map1.put("email","wrong_email");
        result = callAction(
                controllers.user.routes.ref.ResetPasswordController.forgotPwdSendMail(),
                fakeRequest().withFormUrlEncodedBody(map1)
                );
        assertThat(status(result)).isEqualTo(400);
        assertThat(contentAsString(result)).contains("Something went wrong. Weve sent our pink monkeys to fix the problem");

        //for fase 2 we need a user


        Map<String, String> map2 = new HashMap<String,String>();
        map2.put("name", "Jim Jones");
        map2.put("email","jimjones@localhost.com");
        map2.put("bday","13/05/1931");
        map2.put("gender","Male");
        map2.put("prefLanguage","en");
        map2.put("password","kaituma");
        map2.put("controle_passwd","kaituma");

        result = callAction(
                controllers.routes.ref.UserController.register(),fakeRequest().withFormUrlEncodedBody(map2)
                );

        // an email is registed with the user. So it is manditory that it is provided
        Map<String, String> map3 = new HashMap<String,String>();
        map3.put("id", "jjones");

        result = callAction(
                controllers.user.routes.ref.ResetPasswordController.forgotPwdSendMail(),
                fakeRequest().withFormUrlEncodedBody(map3)
                );
        assertThat(status(result)).isEqualTo(400);
        assertThat(contentAsString(result)).contains("Something went wrong. Weve sent our pink monkeys to fix the problem");

        // Adding the correct email should fix this
        map3.put("email","jimjones@localhost.com");


        result = callAction(
                controllers.user.routes.ref.ResetPasswordController.forgotPwdSendMail(),
                fakeRequest().withFormUrlEncodedBody(map3)
                );
        assertThat(status(result)).isEqualTo(200);

        UserModel userModel = Ebean.find(UserModel.class).where().eq("id", "jjones").findUnique();
        String reset_token = userModel.reset_token;
        assertThat(!reset_token.isEmpty());

        result = callAction(
                controllers.user.routes.ref.ResetPasswordController.receivePasswordResetToken(reset_token),
                fakeRequest().withFormUrlEncodedBody(new HashMap<String,String>())
                );

        assertThat(status(result)).isEqualTo(200);
        assertThat(contentAsString(result)).contains("Reset password");
    }

}
