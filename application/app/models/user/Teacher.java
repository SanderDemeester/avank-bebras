
package models.user;

import java.awt.List;
import java.util.ArrayList;

import javax.persistence.Entity;

import controllers.user.Type;
import play.mvc.Result;
import views.html.landingPages.TeacherLandingPage;

/**
 * @author Sander Demeester
 */

@Entity
public class Teacher extends SuperUser{

    /**
     * The constructor of teacher.
     */
    public Teacher(UserID id, Type loginType, String name){
    	super(id,loginType,name);

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

	@Override
	public Result showLandingPage() {
		// TODO lots of stuff
		//return ok(TeacherLandingPage.render("Test", new ArrayList<ClassGroup>()));
		return null;
	}

	@Override
	public Result showStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

}
