
package controllers.User;

import play.mvc.Result;

/**
 * The default abstract class for User.
 * @author sander
**/

public abstract class User {

    private UserID id;
    private String loginType;

    /**
     * Returns info about this user as a String.
     * @return Userinfo.
     */
    public String getUserInfo() {
        return null;
    }

    /**
     * Reset the password of this user. Is delegated to the
     * AuthenticationManager.
     */
    public void resetPassword(){

    }

    /**
     * Logs out the user. Is delegated to the AuthenticationManager.
     */
    public void logout(){

    }

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
