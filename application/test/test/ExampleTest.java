package test;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.fest.assertions.Assertions.assertThat;
import play.mvc.Content;
import play.test.Helpers;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.contentType;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.running;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import views.html.index;
import models.data.Link;

//An example of some basic JUnit tests and integration tests.

public class ExampleTest {
	
	
	@Before
	public void startApp(){
	    Map<String, String> settings = new HashMap<String, String>();
		settings.put("db.default.driver", "org.h2.Driver");
	    settings.put("db.default.user", "sa");
	    settings.put("db.default.password", "");
	    settings.put("db.default.url", "jdbc:h2:mem:play");
	    
		start(fakeApplication(settings));
	}
	
	@After
	public void stopApp(){
		stop(fakeApplication());
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
