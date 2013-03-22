package test_suite_databank;

import generic.WithApplication;
import models.user.Independent;
import models.user.User;
import models.user.UserID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.FakeApplication;
import controllers.user.Type;
import static play.test.Helpers.*;

public class UserDatabaseTest{
	
	
	
	@Before
	public void startApp(){
		
//		start(fakeApplication(inMemoryDatabase()));
		start(fakeApplication());
	}
	
	@Test
	public void test() {
//		User user = new Independent(new UserID("ind"), Type.INDEPENDENT, "Bertrand Russell");
//		user.save();
	}

}
