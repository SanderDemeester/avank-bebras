
package models.user;

import models.dbentities.UserModel;
import play.mvc.Content;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Administrator extends Organizer{

       
    public Administrator(UserModel data) {
		super(data);
		// TODO Auto-generated constructor stub
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

}
