/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.List;

import models.data.Link;
import models.management.ModelState;
import models.util.OperationResultInfo;
import play.mvc.Result;
import views.html.classes.classpupilManagement;
import controllers.EController;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class ClassPupilController extends EController {
	
	public static Result viewClass(String id,int page, String orderBy, String order, String filter){
		List<Link> breadcrumbs = getBreadCrumbs();
		OperationResultInfo ori = new OperationResultInfo();
		int idInt = -1;
		try{
			idInt = Integer.parseInt(id);
		}catch(NumberFormatException nfe){
			//TODO
			return TODO;
		}
		ClassPupilManager cpm = new ClassPupilManager(idInt, ClassPupilManager.DataSet.ALL,//TODO
				ModelState.READ);
		cpm.setFilter(filter);
		cpm.setOrder(order);
		cpm.setOrderBy(orderBy);
		return ok(
				classpupilManagement.render(cpm.page(page),cpm,orderBy,order,filter,breadcrumbs,ori)
				);
		
	}
	
	private static boolean isAuthorized(int id){
		//TODO
		return true;
	}
	
	private static List<Link> getBreadCrumbs(){
		//TODO
		return new ArrayList<Link>();
	}

}
