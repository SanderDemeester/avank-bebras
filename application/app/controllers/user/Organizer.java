
package controllers.user;

import java.util.Set;

import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Organizer extends SuperUser{
	
	public Organizer(){
		
	}

	/**
	 * Show specific landingpage for Organizer.
	 */
	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
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
	public void mimickPupil(Independent user){
		
	}
	
	/**
	 * 
	 * @return return a list of all teachers.
	 */
	public Set<Teacher> listTeachers(){
		return null;
	}
	
	/**
	 * 
	 * @return retuns a Result view to manage Questions.
	 */
	public Result manageQuestions(){
		return null;
	}
	
	/**
	 * 
	 * @return returns a Result view to manage Competitions.
	 */
	public Result manageCompetitions(){
		return null;
	}
	

}
