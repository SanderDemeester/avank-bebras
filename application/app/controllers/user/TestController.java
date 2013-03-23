/**
 * 
 */
package controllers.user;

import java.awt.List;
import java.util.ArrayList;

import com.avaje.ebean.LogLevel;

import models.data.Link;
import models.user.ClassGroup;
import models.user.Independent;
import models.user.Teacher;
import models.user.UserID;
import play.mvc.Result;
import views.html.index;
import views.html.landingPages.TeacherLandingPage;
import controllers.EController;

/**
 * Class purely meant as testclass for Teacher Landing page. Will most likely be deleted once
 * I know how Sander's code works.
 * @author Jens N. Rammant
 *!!!!!!!!TESTCLASS!!!!!!!!
 */
public class TestController extends EController {
	private Teacher teacher;
	
	
	/**
	 * @param teacher
	 */
	public TestController(Teacher teacher) {
		super();
		this.teacher = teacher;
	}

	public static Result showTeachLandingPage() {
		setCommonHeaders();
		Teacher t = new Teacher(new UserID("a"), Type.TEACHER, "Jefke");
		
		return ok(t.getLandingPage());
	}
	
	public static Result testClassesPupil(){
		Independent i = new Independent(new UserID("d"), Type.PUPIL, "Pieter");
		
		return ok(i.getLandingPage());
	}
}
