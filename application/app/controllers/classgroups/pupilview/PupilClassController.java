/**
 * 
 */
package controllers.classgroups.pupilview;

import java.util.ArrayList;
import java.util.List;

import models.data.Link;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.User;

import play.mvc.Result;
import views.html.commons.noaccess;
import controllers.EController;

/**
 * @author Jens N. Rammant
 *
 */
public class PupilClassController extends EController {

	public static Result viewClasses(int page, String orderBy, String order, String filter){
		List<Link> bc = getBreadcrumbs();
		if(!isAuthorized())return ok(noaccess.render(bc));
		
		User current = AuthenticationManager.getInstance().getUser();
		PupilClassManager pcm = new PupilClassManager(current.getID(), ModelState.READ);
		pcm.setOrder(order);
		pcm.setFilter(filter);
		pcm.setOrderBy(orderBy);
		
		
		
		return TODO; //TODO

	}
	
	private static boolean isAuthorized(){
		//TODO
		return true;
	}
	
	private static List<Link> getBreadcrumbs(){
		ArrayList<Link> res = new ArrayList<Link>();
		//TODO
		return res;
	}
}
