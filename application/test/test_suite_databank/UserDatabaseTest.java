package test_suite_databank;

import static org.junit.Assert.*;

import models.user.Administrator;
import models.user.Independent;
import models.user.User;
import models.user.UserID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.user.Type;

import play.test.FakeApplication;
import play.test.Helpers;

public class UserDatabaseTest {
	
	private FakeApplication fakeApplication;
	
	@Before
	public void startApp(){
		fakeApplication = Helpers.fakeApplication(); 
		Helpers.start(fakeApplication);
	}
	
	@After
	public void stopApp(){
		Helpers.stop(fakeApplication);
	}
	
	@Test
	public void test() {
		User user = new Independent(new UserID("ind"), Type.INDEPENDENT, "Bertrand Russell");
		user.save();
	}

}
