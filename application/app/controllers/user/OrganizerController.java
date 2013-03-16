package controllers.user;

import play.mvc.Result;
/**
 *  The purpose of this class is to contain all logic 
 * for returning a view back to the client. This specific view is for an Organizer. 
 * @author Sander Demeester
 */
public class OrganizerController extends AbstractUserController{

	 /**
     * Show specific landingpage for Organizer.
     */
	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
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
