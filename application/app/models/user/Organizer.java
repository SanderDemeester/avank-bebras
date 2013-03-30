
package models.user;

import java.util.Set;

import models.dbentities.UserModel;

import play.mvc.Content;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */

public class Organizer extends SuperUser{

    

    public Organizer(UserModel data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	/**
     * Block user.
     * @param user
     */
    public void blockPupil(User user){

    }

    /**
     * Mimick user.
     * @param user
     */
    public void mimicPupil(Independent user){

    }

    /**
     *
     * @return return a list of all teachers.
     */
    public Set<Teacher> listTeachers(){
        return null;
    }
    
    /*
     * Returns the Question Management Page
     * @return Question Management Page
     */
    public Result manageQuestions(){
    	return null;
    }
    
    /*
     * Returns the Competition Management Page
     * @return Competition Management Page
     */
    public Result manageCompetitions(){
    	return null;
    }

}
