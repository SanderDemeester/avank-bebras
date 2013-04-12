/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.ExpressionList;
import play.mvc.Call;
import models.EMessages;
import models.dbentities.ClassGroup;
import models.management.Manager;
import models.management.ModelState;

/**
 * @author Jens N. Rammant
 *
 */
public class MainClassesManager extends Manager<ClassGroup> {
	
	private String teacherID;

	public MainClassesManager(String teacherID, ModelState state) {
		super(ClassGroup.class, state, "name", "name");
		this.teacherID=teacherID;
	}
	
	@Override
	protected ExpressionList<ClassGroup> getDataSet(){
		ExpressionList<ClassGroup> l = super.getDataSet();
		return l.eq("teacherid", teacherID);
		
	}

	@Override
	public Call getListRoute(int page, String filter) {
		return routes.ClassGroupController.viewClasses(page, orderBy, order, filter);
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
	
	@Override
	public List<String> getColumnHeaders(){
		ArrayList<String> res = new ArrayList<String>();
		res.add("id");
		res.add("name");
		res.add("schoolid");
		res.add("level");
		res.add("expdate");
		res.add("isactive");
		return res;
	}

}
