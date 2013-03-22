package test_suite_databank;

import models.user.Independent;
import models.user.User;
import models.user.UserID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.FakeApplication;
import controllers.user.Type;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

public class UserDatabaseTest {
	
	private FakeApplication application;
	
	
	@Before
	public void startApp(){
		application = fakeApplication();
		start(application);
	}
	
	@After
	public void stopApp(){
		stop(application);
	}
	
	@Test
	public void test() {
		User user = new Independent(new UserID("ind"), Type.INDEPENDENT, "Bertrand Russell");
		user.save();
	}

}
