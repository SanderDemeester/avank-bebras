package controllers.user;

import play.mvc.Result;

/*
 * The purpose of this class is to create a abstract type for all UserControllers. 
 * Different roles need different view handlers. The basis for all handlers is this class.
 *  @author Sander Demeester
 */
public abstract class AbstractUserController {
	
	/**
     * The returned page is where the user arrives after login in.
     * @return The user's landing page.
     */
    public abstract Result showLandingPage();

    /**
     * Redirects the user to the statistics page. This is basically delegated to
     * the statistics subsystem.
     * @return The statistics page.
     */
    public Result showStatistics(){
        //TODO: add proper driver code.
        return null;
    }

    /**
     * Redirects the user to the page where he can view and change his personal
     * information.
     * @return The personal information page.
     */
    public Result showPersonalInformation(){
        //TODO: add proper driver code.
        return null;
    }

}
