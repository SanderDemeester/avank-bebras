
package models.user;

import models.dbentities.UserModel;
import play.mvc.Content;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Administrator extends Organizer{

    public Administrator(UserModel data) {
        super(data, UserType.ADMINISTRATOR);
        ROLES.add(Role.MANAGEFAQ); //Give admin the ability to manage FAQ
        ROLES.add(Role.MANAGECONTESTS);
        ROLES.add(Role.DATAMANAGER);
    }

    /*
     * returns the management page for the homepage links
     * @return Homepage Links Management page
     */
    public Result manageHomepageLinks(){
        return null;
    }

    /*
     * returns the grades page
     * @return Grades Page
     */
    public Result manageGrades(){
        return null;
    }

    /*
     * returns difficulties page
     * @result Difficulties Page
     */
    public Result manageDifficulties(){
        return null;
    }

    /*
     * return FAQ management page
     * @return FAQ Management Page
     */
    public Result manageFAQ(){
        return null;
    }

    @Override
    public Content getLandingPage(){
        //TODO
        return null;
    }
}
