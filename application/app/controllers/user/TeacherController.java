/**
 * 
 */
package controllers.user;

import java.util.ArrayList;

import models.user.ClassGroup;
import models.user.Teacher;
import play.mvc.Result;
import views.html.landingPages.TeacherLandingPage;
import controllers.EController;

/**
 * Class purely meant as testclass for Teacher Landing page. Will probably be deleted
 * @author Jens N. Rammant
 *
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
		// TODO lots of stuff
		ArrayList<ClassGroup> cc = new ArrayList<>();
		cc.add(new ClassGroup());
		ArrayList<ClassGroup> pc = new ArrayList<>();
		pc.add(new ClassGroup());
		pc.add(new ClassGroup());
		return ok(TeacherLandingPage.render("Test", cc, pc));
	}
}
