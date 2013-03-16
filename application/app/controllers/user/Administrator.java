
package controllers.user;

import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Administrator extends Organizer{

    /**
     * Constructor for Administrator.
     **/
    public Administrator(){
        super();
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
