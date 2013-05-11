/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import models.dbentities.ClassPupil;
import models.dbentities.UserModel;
import models.management.Manager;
import models.management.ModelState;
import models.user.ChainOfCommand;
import play.mvc.Call;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;

/**
 * @author Jens N. Rammant
 */
public class ClassPupilManager extends Manager<UserModel> {

	//The class this manager manages
	private int classID;
	//Which subset of pupils to use
	private DataSet data;
	//Determines whether the remove link does anything or not. Standard is false
	private boolean canRemove;
	
	/**
	 * Make a new ClassPupilManager
	 * @param classID id of the class
	 * @param data the dataset
	 * @param state state of the model
	 */
	public ClassPupilManager(int classID, DataSet data, ModelState state) {
		super(UserModel.class, state, "id", "name");
		this.classID=classID;
		this.data = data;
		this.canRemove = false;
	}

	@Override
	public Call getListRoute(int page, String orderBy, String order, String filter) {
		return routes.ClassPupilController.viewClass(classID, page, orderBy, order, filter);
	}

	@Override
	public Call getAddRoute() {
		return routes.ClassPupilController.addExistingStudent(classID);
	}

	@Override
	public Call getEditRoute(String id) {
		if(ChainOfCommand.isSuperiorOf(id))return controllers.user.routes.OtherUserController.show(id);
		return null;
	}

	@Override
	public Call getRemoveRoute(String id) {
		if(canRemove){
			return routes.ClassPupilController.removeStudent(classID,id);
		}
		return null;
	}

	@Override
	public play.api.mvc.Call getSaveRoute() {
		// not used
		return null;
	}

	@Override
	public play.api.mvc.Call getUpdateRoute() {
		// not used
		return null;
	}

	@Override
	public String getMessagesPrefix() {
		return "classes.pupil";
	}
	
	@Override
	protected ExpressionList<UserModel> getDataSet(){
		//Retrieve all the ClassPupil objects that are linked to this class & extract ids
		Collection<String> pupIDs = new ArrayList<String>();
		Collection<ClassPupil> cp = Ebean.find(ClassPupil.class).where().eq("classid", classID).findList();
		for(ClassPupil c : cp)pupIDs.add(c.indid);
		
		Expression active = Expr.eq("classgroup", classID); //Find all active students
		Expression nonActive = Expr.in("id", pupIDs); //Find all non-active students
		if(data==DataSet.ACTIVE)
			return super.getDataSet().add(active);
		if(data==DataSet.NOTACTIVE)
			return super.getDataSet().add(nonActive);	
		if(data==DataSet.ALL)
			return super.getDataSet().or(active, nonActive); //Find all
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
	
	/**
	 * 
	 * @param b whether the remove link works or not
	 */
	public void setCanRemove(boolean b){
		this.canRemove = b;
	}
	
	/**
	 * Dataset
	 */
	public enum DataSet {
	    /**
	     * Active
	     */
		ACTIVE,
		/**
		 * Not active
		 */
		NOTACTIVE,
		/**
		 * All
		 */
		ALL
	}

}
