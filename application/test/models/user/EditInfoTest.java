package models.user;

import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.fest.assertions.Assertions.assertThat;

import com.avaje.ebean.Ebean;

import play.mvc.Result;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;
import static play.test.Helpers.contentAsString;

import models.dbentities.UserModel;

public class EditInfoTest extends test.ContextTest {


    public void createAndEdit(){
    	
        //create dummy user (thanks to Sander)
        Map<String, String> map = new HashMap<String,String>();
        map.put("name", "Jim Jones");
        map.put("email","jimjones@localhost.com");
        map.put("bday","1931/05/13");
        map.put("gender","Male");
        map.put("prefLanguage","en");
        map.put("password","kaituma");
        map.put("controle_passwd","kaituma");

        Result result = callAction(
            controllers.routes.ref.UserController.register(),fakeRequest().withFormUrlEncodedBody(map)
        );

        assertThat(status(result)).isEqualTo(200);
        assertThat(contentAsString(result)).contains("Your Bebras ID is: jimjones.");
        assertThat(contentAsString(result)).contains("You may login with your ID and password.");

 
        // check if editing personal information succeeds
        map = new HashMap<String,String>();
        map.put("name", "Jim Test Jones");
        map.put("email","jimtestjones@localhost.com");
        map.put("bday","1931/05/13");
        map.put("gender","Male");
        map.put("prefLanguage","nl");        
                      
        result = callAction(
            controllers.user.routes.ref.PersonalPageController.show(1),fakeRequest().withFormUrlEncodedBody(map)
        );
     
        assertNotNull(Ebean.find(UserModel.class).where().eq("id","jimjones").where().eq("type", UserType.PUPIL_OR_INDEP.toString()).findUnique());
        assertNotNull(Ebean.find(UserModel.class).where().eq("id","jimjones").where().eq("name", "Jim Test Jones"));
        assertNotNull(Ebean.find(UserModel.class).where().eq("id","jimjones").where().eq("email", "jimtestjones@localhost.com"));
        assertNotNull(Ebean.find(UserModel.class).where().eq("id","jimjones").where().eq("prefLanguage", "nl"));
    }
}
