/**
 * 
 */
package controllers.contactform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.mail.UpgradeRequestMail;
import models.user.AuthenticationManager;
import models.user.User;
import models.user.UserType;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.commons.noaccess;
import views.html.contactform.upgradeRequest;
import controllers.EController;

/**
 * @author Jens N. Rammant
 *
 */
public class RequestTeacherController extends EController {

	/**
	 * 
	 * @return a page to upload the image
	 */
	public static Result showForm(){
		//Generate breadcrumbs
		List<Link> breadcrumbs = getBreadcrumbs();
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		//Check if the user has an emailaddress
		User current = AuthenticationManager.getInstance().getUser();
		if(current==null||current.data==null||current.data.email==null){
			flash("warning",EMessages.get("contact.requestupgrade.noemail"));
		}
		//return page
		return ok(upgradeRequest.render(breadcrumbs));
	}
	
	/**
	 * Tries to send the mail, if possible
	 * 
	 * @return the upload page
	 */
	public static Result upload() {
		// Generate breadcrumbs
		List<Link> breadcrumbs = getBreadcrumbs();
		if (!isAuthorized())
			return ok(noaccess.render(breadcrumbs));
		// Check if current user has emailaddress
		User current = AuthenticationManager.getInstance().getUser();
		if (current == null || current.data == null
				|| current.data.email == null) {
			flash("warning", EMessages.get("contact.requestupgrade.noemail"));
			return redirect(routes.RequestTeacherController.showForm());
		}
		// Retrieve uploaded data
		try {
			MultipartFormData data = request().body().asMultipartFormData();

			FilePart card = data.getFile("card");
			File file = card.getFile();
			// Doesn't work atm since isImage isn't implemented
			if (!isImage(file)) {
				flash("warning",
						EMessages.get("contact.requestupgrade.notimage"));
				return redirect(routes.RequestTeacherController.showForm());
			}
			// Create and try to send mail
			UpgradeRequestMail urm = new UpgradeRequestMail(file,card.getFilename());

			urm.send();
			flash("success", EMessages.get("contact.form.sendsuccess"));

		} catch (Exception e) {
			flash("error", EMessages.get("contact.form.couldnotsend"));
		}
		return redirect(routes.RequestTeacherController.showForm());
	}
	
	/**
	 * 
	 * @return list with the breadcrumb links
	 */
	private static List<Link> getBreadcrumbs(){
		List<Link> breadcrumbs = new ArrayList<Link>();
		breadcrumbs.add(new Link("Home","/"));
		breadcrumbs.add(new Link(EMessages.get("contact.upgraderequest"),"/upgrade"));
		return breadcrumbs;
	}
	
	/**
	 * 
	 * @return if user is authorized
	 */
	private static boolean isAuthorized(){
		User current = AuthenticationManager.getInstance().getUser();
		return current.getType()==UserType.PUPIL_OR_INDEP;
	}
	
	/**
	 * Not yet implemented
	 * @param file file to check
	 * @return whether file is an image
	 */
	private static boolean isImage(File file){
		//TODO might be useful, but is rather difficult
		return true;
	}
}
