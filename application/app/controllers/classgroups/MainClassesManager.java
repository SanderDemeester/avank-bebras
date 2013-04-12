/**
 * 
 */
package controllers.classgroups;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;

import play.db.ebean.Model.Finder;
import play.mvc.Call;
import models.dbentities.ClassGroup;
import models.management.ManageableModel;
import models.management.Manager;
import models.management.ModelState;

/**
 * @author Jens N. Rammant
 *
 */
public class MainClassesManager extends Manager<ClassGroup> {
	
	private String teacherID;

	public MainClassesManager(String teacherID, Class<ClassGroup> modelClass, ModelState state,
			String orderBy, String filterBy) {
		super(modelClass, state, orderBy, filterBy);
		this.teacherID=teacherID;
	}
	
	@Override
	protected ExpressionList<ClassGroup> getDataSet(){
		//TODO
		return null;
		
	}

	@Override
	public Call getListRoute(int page, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getAddRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getEditRoute(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getRemoveRoute(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public play.api.mvc.Call getSaveRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public play.api.mvc.Call getUpdateRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessagesPrefix() {
		return "classes.main";
	}

}
