/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.List;

import models.data.Link;

import play.mvc.Result;
import views.html.commons.noaccess;
import controllers.EController;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class ClassGroupController extends EController {

	public Result viewClasses(){
		List<Link> breadcrumbs = getBreadcrumbs();
		
		if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
		//TODO
		return null;
	}
	
	private boolean isAuthorized(){
		//TODO
		return true;
	}
	
	private List<Link> getBreadcrumbs(){
		//TODO
		return new ArrayList<Link>();
	}
	
}
