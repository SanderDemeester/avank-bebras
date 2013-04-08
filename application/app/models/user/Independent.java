
package models.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import models.dbentities.ClassGroup;
import models.dbentities.ClassPupil;
import models.dbentities.UserModel;

import com.avaje.ebean.Ebean;

import play.mvc.Content;
import play.mvc.Result;

/**
 * @author Sander Demeester
 * @author Jens N. Rammant
 */

public class Independent extends Authenticated{

    private List<String> previousClassList;

    protected Independent(UserModel data, UserType type){
        super(data, type); //abstract class constructor could init some values
        previousClassList = new ArrayList<String>();
    }
    
    public Independent(UserModel data) {
        this(data, UserType.INDEPENDENT);
    }

    /**
     * Add an old class
     * @param oldClass
     */
    public void addPreviousClass(String oldClass){
        previousClassList.add(oldClass);
    }

    /**
     * Add a class to Independent user.
     * @param classGroup
     */
    public void addCurrentClass(ClassGroup classGroup){

    }

    public ClassGroup getCurrentClass(){
    	//TODO safety
        return Ebean.find(ClassGroup.class).where().eq("id", this.data.classgroup).findUnique();
    }

	@Override
	public Content getLandingPage() {
		
		//TODO
		return null;
	}

	@Override
	public Result showStatistics() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Queries the database for all previous classes the user is associated with
	 * @return list of previous classes 
	 */
	public Collection<ClassGroup> getPreviousClasses(){
		//TODO safety
		ArrayList<ClassGroup> res = new ArrayList<ClassGroup>();
		
		List<ClassPupil> cp = Ebean.find(ClassPupil.class).where().eq("indid", this.data.id).findList();
		for(ClassPupil c : cp){
			ClassGroup cg = Ebean.find(ClassGroup.class).where().eq("id", c.classid).findUnique();
			if(cg != null)res.add(cg);
		}
		
		return res;
	}

}
