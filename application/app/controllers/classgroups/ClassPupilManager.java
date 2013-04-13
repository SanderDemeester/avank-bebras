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
import models.dbentities.ClassPupil;
import models.dbentities.UserModel;
import models.management.Manager;
import models.management.ModelState;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class ClassPupilManager extends Manager<UserModel> {

	private int classID;
	private DataSet data;
	//Determines whether the remove link does anything or not. Standard is false
	private boolean canRemove;
	
	public ClassPupilManager(int classID, DataSet data, ModelState state) {
		super(UserModel.class, state, "id", "name");
		this.classID=classID;
		this.data = data;
		this.canRemove = false;
	}

	@Override
	public Call getListRoute(int page, String filter) {
		return routes.ClassPupilController.viewClass(Integer.toString(classID), page, orderBy, order, filter);
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
		if(canRemove){
			return routes.ClassPupilController.removeStudent(Integer.toString(classID),id);
		}
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
		return "classes.pupil";
	}
	/**
	 * TODO write explanation
	 */
	@Override
	protected ExpressionList<UserModel> getDataSet(){
		Collection<String> pupIDs = new ArrayList<String>();
		Collection<ClassPupil> cp = Ebean.find(ClassPupil.class).where().eq("classid", classID).findList();
		for(ClassPupil c : cp)pupIDs.add(c.indid);
		
		Expression active = Expr.eq("classgroup", classID);
		Expression nonActive = Expr.in("id", pupIDs);
		if(data==DataSet.ACTIVE)
			return super.getDataSet().add(active);
		if(data==DataSet.NOTACTIVE)
			return super.getDataSet().add(nonActive);	
		if(data==DataSet.ALL)
			return super.getDataSet().or(active, nonActive);
		return null;
		
	}
	
	@Override
	public List<String> getColumnHeaders(){
		ArrayList<String> res = new ArrayList<String>();
		res.add("id");
		res.add("name");
		res.add("gender");
		res.add("birthdate");
		res.add("preflanguage");
		res.add("active");
		return res;
	}
	
	public void setCanRemove(boolean b){
		this.canRemove = b;
	}
	
	public enum DataSet {
		ACTIVE,
		NOTACTIVE,
		ALL
	}

}
