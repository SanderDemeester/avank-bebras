
package models.user;

import javax.persistence.Entity;

import controllers.user.Type;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */
@Entity
public class Administrator extends Organizer{

    /**
     * Constructor for Administrator.
     **/
    public Administrator(UserID id, Type loginType, String name){
        super(id,loginType,name);
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

}
