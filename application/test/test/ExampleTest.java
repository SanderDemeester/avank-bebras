package test;

import org.fest.assertions.AssertExtension;
import org.junit.*;

import controllers.user.Type;
import play.mvc.*;
import play.test.*;
import views.html.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.data.Link;
import models.user.Administrator;
import models.user.User;
import models.user.UserID;

import java.util.List;
import java.util.ArrayList;

//An example of some basic JUnit tests and integration tests.

public class ExampleTest {
	
	/**
	 * First way, start a global test.
	 */
	
	@BeforeClass
	public static void  startApp(){
		Helpers.start(fakeApplication(Helpers.inMemoryDatabase()));
	}
	
	/**
	 * The second way to start tests.
	 */
	@Test
	public void testUser(){
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
	        public void run() { //some testing goes here
	        	// This wont yet work because admin is not yet a registerd entity to ebeans, 
	        	// We need inheritance 
	        	//new Administrator(new UserID("id"),Type.ADMINISTRATOR,"Bertrand Russell").save();
	        	}
	        });	

	}
	
	
	@Test
	public void createSimpelUser(){
		
	}
	
	@Test
	public void testTemplate(){
		
//	    Index is the name of our scala template.
//		The scala source file takes one string argument thet the template will render
        List links = new ArrayList<Link>();
        links.add(new Link("Bebras", "http://www.bebras.be"));
		Content htmlContent = index.render("Test-string", links);
		
//		Test the content Type
		assertThat(contentType(htmlContent)).isEqualTo("text/html");
//		Test that the html Content contains a string
		assertThat(contentAsString(htmlContent)).contains("Test-string");
	}
	
}
