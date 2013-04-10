/**
 * 
 */
package controllers.schools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import models.EMessages;
import models.data.Link;
import models.dbentities.SchoolModel;
import models.user.AuthenticationManager;
import models.user.Teacher;
import models.user.UserType;
import models.util.OperationResultInfo;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Results;
import views.html.commons.noaccess;
import views.html.schools.addschool;
import views.html.schools.schools;
import controllers.EController;

/**
 * @author Jens N. Rammant
 */
public class SchoolController extends EController {

	/**
	 * 
	 * @return a page of all the schools the teacher is somehow associated with.
	 *         No Access page if user is not teacher.
	 */
	public static Result viewSchools() {
		// TODO: possible improvement: use DMTV (or at least make it look
		// similar)

		// Generate breadcrumbs & template arguments
		ArrayList<Link> breadcrumbs = getBreadcrumbs();
		if (!isAuthorized())
			return ok(noaccess.render(breadcrumbs));
		OperationResultInfo ori = new OperationResultInfo();

		// Retrieve list of schools
		ArrayList<SchoolModel> list = new ArrayList<SchoolModel>();
		Teacher t = getTeacher();
		try {
			list.addAll(t.getSchools());
		} catch (PersistenceException pe) {
			// Could not retrieve list of schools, add error
			list.clear();
			ori.add(EMessages.get("schools.list.error"),
					OperationResultInfo.Type.ERROR);
		}
		// Sort the list of schools
		Collections.sort(list);
		return ok(schools.render(breadcrumbs, ori, list));
	}

	/**
	 * 
	 * @return a page for creating a new school. No Access page if user is not a
	 *         teacher
	 */
	public static Result create() {
		// Generate breadcrumbs
		List<Link> breadcrumbs = getBreadcrumbs();
		breadcrumbs.add(new Link(EMessages.get("schools.add"), "/schools/new"));
		OperationResultInfo ori = new OperationResultInfo();
		// Check if authorized
		if (!isAuthorized())
			return ok(noaccess.render(breadcrumbs));
		// Create & render form
		Form<SchoolModel> form = new Form<SchoolModel>(SchoolModel.class);
		return ok(addschool.render(form, breadcrumbs, ori));
	}

	/**
	 * Saves the data from the form
	 * 
	 * @return the list page if succesfull. Otherwise the form page with an
	 *         error
	 */
	public static Result save() {
		// Generate breadcrumbs
		List<Link> breadcrumbs = getBreadcrumbs();
		breadcrumbs.add(new Link(EMessages.get("schools.add"), "/schools/new"));
		if (!isAuthorized())
			return ok(noaccess.render(breadcrumbs)); // Check if authorized

		// Retrieve the form
		Form<SchoolModel> form = form(SchoolModel.class).bindFromRequest();
		if (form.hasErrors()) {
			// Form was not complete --> return form with a warning
			OperationResultInfo ori = new OperationResultInfo();
			ori.add(EMessages.get("schools.error.notcomplete"),
					OperationResultInfo.Type.WARNING);
			return badRequest(addschool.render(form, breadcrumbs, ori));
		}
		// Try to save the info
		SchoolModel m = form.get();
		try {
			Teacher t = getTeacher();
			m.orig = t.data.id; // Add teacher's id as 'originator'
			m.save();
		} catch (Exception p) {
			// Something went wrong in the saving. Redirect back to the create
			// page with an error alert
			OperationResultInfo ori = new OperationResultInfo();
			ori.add(EMessages.get("schools.error.savefail"),
					OperationResultInfo.Type.ERROR);
			return badRequest(addschool.render(form, breadcrumbs, ori));
		}
		// Redirect back to the list
		flash("success", Integer.toString(m.id)); // Show id of newly created
													// school in message
		return Results.redirect(controllers.schools.routes.SchoolController
				.viewSchools());

	}

	/**
	 * 
	 * @return the standard breadcrumbs for school management
	 */
	public static ArrayList<Link> getBreadcrumbs() {
		ArrayList<Link> res = new ArrayList<Link>();
		res.add(new Link("Home", "/"));
		res.add(new Link(EMessages.get("schools.title"), "/schools"));
		return res;
	}

	/**
	 * 
	 * @return whether the user is authorized to view a School Management page
	 */
	private static boolean isAuthorized() {
		// TODO test if works once it's possible to login as a teacher
		// user is authorized if they're a teacher
		return AuthenticationManager.getInstance().getUser().data.type == UserType.TEACHER;
	}

	/**
	 * 
	 * @return the currently logged in Teacher. The behaviour when it's not a
	 *         teacher who's logged in is not defined. (Use together with
	 *         isAuthorized)
	 */
	private static Teacher getTeacher() {
		// TODO test if works once it's possible to login as a teacher
		return (Teacher) AuthenticationManager.getInstance().getUser();

	}
}
