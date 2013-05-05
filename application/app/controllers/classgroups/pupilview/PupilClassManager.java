/**
 * 
 */
package controllers.classgroups.pupilview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import play.mvc.Call;
import models.dbentities.ClassGroup;
import models.dbentities.ClassPupil;
import models.dbentities.UserModel;
import models.management.Manager;
import models.management.ModelState;
import models.user.Independent;

/**
 * @author Jens N. Rammant
 * Manager for a pupil's old classes
 */
public class PupilClassManager extends Manager<ClassGroup> {
	
	private String pupilID;

	public PupilClassManager(String pupilID, ModelState state) {
		super(ClassGroup.class, state, "name", "name");
		this.pupilID=pupilID;
	}

	@Override
	public Call getListRoute(int page, String orderBy, String order,
			String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessagesPrefix() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected ExpressionList<ClassGroup> getDataSet(){
		//Get the pupil's Used class
		UserModel pup = Ebean.find(UserModel.class, pupilID);
		//List with the class ids
		List<Integer> ids = new ArrayList<Integer>();
		if(pup!=null){
			Independent pupil = new Independent(pup);
			Collection<ClassGroup> classes = pupil.getPreviousClasses();
			for(ClassGroup cg : classes)ids.add(cg.id);
		}
		//Return an expressionlist
		return super.getDataSet().where().in("id", ids);
		
		
	}
	

}
