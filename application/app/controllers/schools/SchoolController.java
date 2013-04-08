/**
 * 
 */
package controllers.schools;

import java.util.ArrayList;
import java.util.Date;

import models.EMessages;
import models.data.Link;
import models.dbentities.SchoolModel;
import models.dbentities.UserModel;
import models.user.Gender;
import models.user.Teacher;
import models.user.UserType;
import models.util.OperationResultInfo;
import play.mvc.Result;
import views.html.schools.schools;
import controllers.EController;

/**
 * @author Jens N. Rammant
 * TODO comments
 * TODO add link to teacher
 */
public class SchoolController extends EController {

	public static Result viewSchools(){
		//TODO check permissions
		
		//Generate breadcrumbs & template arguments
		ArrayList<Link> breadcrumbs = getBreadcrumbs();
		OperationResultInfo ori = new OperationResultInfo();
		ArrayList<SchoolModel> list = new ArrayList<SchoolModel>();
		//TODO get actual Teacher instead of test
		Teacher t = new Teacher(new UserModel("a",UserType.TEACHER, "aa", new Date(17), new Date(17), "aa", "aa", "aa", Gender.Other, "gg"));
		list.addAll(t.getSchools());
		return ok(schools.render(breadcrumbs,ori,list));
	}
	
	public static ArrayList<Link> getBreadcrumbs(){
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home","/"));
		res.add(new Link(EMessages.get("schools.title"),"/schools"));
		return res;
	}
}
