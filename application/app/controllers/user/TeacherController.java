/**
 * 
 */
package controllers.user;

import java.awt.List;
import java.util.ArrayList;

import models.user.ClassGroup;
import models.user.Teacher;
import models.user.UserID;
import play.mvc.Result;
import views.html.landingPages.TeacherLandingPage;
import controllers.EController;

/**
 * Class purely meant as testclass for Teacher Landing page. Will most likely be deleted once
 * I know how Sander's code works.
 * @author Jens N. Rammant
 *!!!!!!!!TESTCLASS!!!!!!!!
 */
public class TeacherController extends EController {
	private Teacher teacher;
	
	
	/**
	 * @param teacher
	 */
	public TeacherController(Teacher teacher) {
		super();
		this.teacher = teacher;
	}

	public static Result showLandingPage() {
		setCommonHeaders();
		Teacher t = new Teacher(new UserID("a"), Type.TEACHER, "Jefke");
		
		return ok(t.getLandingPage());
	}
}
