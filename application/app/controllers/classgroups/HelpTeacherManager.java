/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import play.mvc.Call;
import models.dbentities.HelpTeacher;
import models.dbentities.UserModel;
import models.management.Manager;
import models.management.ModelState;

/**
 * @author Jens N. Rammant
 *
 */
public class HelpTeacherManager extends Manager<UserModel> {

	private int classID;
	public HelpTeacherManager(int classID,ModelState state) {
		super(UserModel.class, state, "id", "name");
		this.classID = classID;
	}
	
	@Override
	protected ExpressionList<UserModel> getDataSet(){
		Collection<HelpTeacher> helpTeacher = Ebean.find(HelpTeacher.class).where().eq("classid", classID).findList();		
		Collection<String> teacherIDs = new ArrayList<String>();
		for(HelpTeacher h : helpTeacher){
			teacherIDs.add(h.teacherid);
		}
		return super.getDataSet().in("id", teacherIDs);
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
	
	@Override
	public Call getListRoute(int page, String filter) {
		return routes.HelpTeacherController.viewHelp(Integer.toString(classID), page, orderBy, order,filter);

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
		return routes.HelpTeacherController.removeHelp(Integer.toString(classID),id);
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
		return "classes.helpteacher";
	}

}
