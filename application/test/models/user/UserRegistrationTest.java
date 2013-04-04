package models.user;

import models.dbentities.UserModel;

import org.junit.*;
import java.util.*;
import play.mvc.*;
import play.templates.Hash;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;


import org.junit.Test;

import com.avaje.ebean.Ebean;

import test.ContextTest;
import play.mvc.*;
import static play.test.Helpers.*;
public class UserRegistrationTest extends ContextTest{

	
	@Test
	public void createAccountSucces(){
		
		Map map = new HashMap<String,String>();
		map.put("fname", "Jim");
		map.put("lname","Jones");
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
		assertThat(contentAsString(result)).contains("Your Bebras ID is: jijones.");
        assertThat(contentAsString(result)).contains("U kan inloggen met uw ID en uw wachtwoord.");
		
		result = callAction(
				controllers.routes.ref.UserController.register(),fakeRequest().withFormUrlEncodedBody(map)
				);
		
		assertThat(contentAsString(result)).contains("Er bestaat al een gebruiker met het gekozen email address");
		
		assertNotNull(Ebean.find(UserModel.class).where().eq("id","jijones").where().eq("type", UserType.INDEPENDENT.toString()).findUnique());
		map.remove("gender");
		
		result = callAction(
				controllers.routes.ref.UserController.register(),fakeRequest().withFormUrlEncodedBody(map)
				);
		assertThat(status(result)).isEqualTo(BAD_REQUEST);
				
	}
}
