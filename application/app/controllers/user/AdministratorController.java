package controllers.user;

import play.mvc.Result;
/**
 * The purpose of this class is to contain all logic 
 * for returning a view back to the client. This specific view is for an Administrator.
 * @author Sander Demeester
 */
public class AdministratorController extends AbstractUserController {
	
	
	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * This methode contains the logic to generate the hompage links.
     * @return returns a Result view to the homepage links.
     */
    public Result manageHomePageLinks(){
        return null;
    }

    /**
     * This methode contains the logic to generate a view to manage grades
     * @return returns a Result view for grades.
     */
    public Result manageGrades(){
        return null;
    }

    /**
     * Thie methode contains the logic to generate a view to manage
     * the different difficulties.
     * @return returns a Result view to manage Difficulties.
     */
    public Result manageDifficulties(){
        return null;
    }

    /**
     *
     * @return returns a Result view to manage the FAQ.
     */
    public Result manageFAQ(){
        return null;
    }


}
