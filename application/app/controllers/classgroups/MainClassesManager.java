/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;
import play.mvc.Call;
import models.dbentities.ClassGroup;
import models.dbentities.HelpTeacher;
import models.management.Manager;
import models.management.ModelState;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class MainClassesManager extends Manager<ClassGroup> {
	
	protected String teacherID;

	public MainClassesManager(String teacherID, ModelState state) {
		super(ClassGroup.class, state, "name", "teacherid");
		this.teacherID=teacherID;
	}
	
	@Override
	protected ExpressionList<ClassGroup> getDataSet(){
		Collection<HelpTeacher> ht = Ebean.find(HelpTeacher.class).where().eq("teacherid", teacherID).findList();
    	Collection<Integer> helpIDs = new ArrayList<Integer>();
		for(HelpTeacher h : ht){
    		helpIDs.add(h.classid);
    	}
		Expression ex1 = Expr.eq("teacherid", teacherID);
		Expression ex2 = Expr.in("id", helpIDs);
		ExpressionList<ClassGroup> l = super.getDataSet();
		return l.or(ex1, ex2);
		
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
		return routes.ClassPupilController.viewClass(id, 0, "name", "asc", "");
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
		res.add("teacherid");
		res.add("level");
		res.add("expdate");
		res.add("isactive");
		return res;
	}

}
