/**
 * 
 */
package controllers.contactform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;

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

	public static Result showForm(){
		List<Link> breadcrumbs = getBreadcrumbs();
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		
		User current = AuthenticationManager.getInstance().getUser();
		if(current==null||current.data==null||current.data.email==null){
			flash("warning",EMessages.get("contact.requestupgrade.noemail"));
		}
		
		return ok(upgradeRequest.render(breadcrumbs));
	}
	
	public static Result upload(){
		List<Link> breadcrumbs = getBreadcrumbs();
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		
		User current = AuthenticationManager.getInstance().getUser();
		if(current==null||current.data==null||current.data.email==null){
			flash("warning",EMessages.get("contact.requestupgrade.noemail"));
			return redirect(routes.RequestTeacherController.showForm());
		}
		
		MultipartFormData data = request().body().asMultipartFormData();
		FilePart card = data.getFile("card");
		File file = card.getFile();
		
		if(!isImage(file)){
			flash("warning",EMessages.get("contact.requestupgrade.notimage"));
			return redirect(routes.RequestTeacherController.showForm());
		}
		UpgradeRequestMail urm = new UpgradeRequestMail(file);
		try {
			urm.send();
			flash("success",EMessages.get("contact.form.sendsuccess"));
		} catch (MessagingException e) {
			flash("error",EMessages.get("contact.form.couldnotsend"));
		}
		return redirect(routes.RequestTeacherController.showForm());
	}
	
	private static List<Link> getBreadcrumbs(){
		List<Link> breadcrumbs = new ArrayList<Link>();
		breadcrumbs.add(new Link("Home","/"));
		breadcrumbs.add(new Link(EMessages.get("contact.upgraderequest.title"),"/upgrade"));
		return breadcrumbs;
	}
	
	private static boolean isAuthorized(){
		User current = AuthenticationManager.getInstance().getUser();
		return current.getType()==UserType.INDEPENDENT||current.getType()==UserType.PUPIL;
	}
	
	private static boolean isImage(File file){
		//TODO might be useful, but is rather difficult
		return true;
	}
}
