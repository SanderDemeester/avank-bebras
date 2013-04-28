/**
 * 
 */
package controllers.contactform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.user.AuthenticationManager;
import models.user.User;

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
		
		MultipartFormData data = request().body().asMultipartFormData();
		FilePart card = data.getFile("card");
		File file = card.getFile();
		
		if(!isImage(file)){
			//TODO
		}
		//TODO
		return TODO;
	}
	
	private static List<Link> getBreadcrumbs(){
		List<Link> breadcrumbs = new ArrayList<Link>();
		breadcrumbs.add(new Link("Home","/"));
		breadcrumbs.add(new Link(EMessages.get("contact.upgraderequest.title"),"/upgrade"));
		return breadcrumbs;
	}
	
	private static boolean isAuthorized(){
		//TODO
		return true;
	}
	
	private static boolean isImage(File file){
		//TODO
		return true;
	}
}
