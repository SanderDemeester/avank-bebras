
package models.user;

import java.util.Collection;

import javax.persistence.PersistenceException;

import models.dbentities.ClassGroup;
import models.dbentities.SchoolModel;
import models.dbentities.UserModel;
import play.mvc.Result;
import play.mvc.Content;
import com.avaje.ebean.Ebean;

/**
 * @author Sander Demeester
 * @author Jens N. Rammant
 */

public class Teacher extends SuperUser{



    public Teacher(UserModel data) {
		super(data, UserType.TEACHER);
	}
    public void scheduleUnrestrictedCompetition(){

    }

    /**
     * @param regex A regex for filtering.
     * Applys a seach filter for the teacher to Filter through all students in the System
     */
    public void searchStudents(String regex){
        //TODO: Need to add some filtering system
    }


    /**
     * @return A view to manageClassGroups.
     */
    public Result manageClasses(){
        return null;
    }

    /**
     * @return A view to manageCompetitions.
     */
    public Result manageCompetitions(){
        return null;
    }	
    /*
     * Creates the personalized landing page for this instance of Teacher.
     * @return Personalized landing page for this instance of teacher
     */
    public Content getLandingPage(){
        //TODO
        return null;
    }

    @Override
    public Result showStatistics() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Queries the database for all Classes that this Teacher is main teacher of
     * @return a collection of classes that this Teacher is main teacher of
     * @throws PersistenceException when something goes wrong during the retrieval
     */
    public Collection<ClassGroup> getClasses() throws PersistenceException{

        java.util.List<ClassGroup> res = Ebean.find(ClassGroup.class).where()
                .eq("teacherid", this.data.id).findList();

        return res;
    }
    
    /**
     * Queries the database for all Schools the Teacher either created or
     * is associated with via a class he teaches/taught.
     * @return a list of schools the teacher is/was associated with
     * @throws PersistenceException when something goes wrong during the retrieval
     */
    public Collection<SchoolModel> getSchools() throws PersistenceException{
    	//TODO
    	//TODO jUnit test
    	return null;
    }

}
