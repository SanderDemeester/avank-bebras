
package models.user;

import java.util.Collection;

import models.dbentities.ClassGroup;
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

    /*
     * Queries the database for all Classes that this Teacher is main teacher of
     * @return List of all ClassGroups this Teacher is main Teacher of
     */
    public Collection<ClassGroup> getClasses(){

        java.util.List<ClassGroup> res = Ebean.find(ClassGroup.class).where()
                .eq("teacherid", this.data.id).findList();

        return res;
    }

}
