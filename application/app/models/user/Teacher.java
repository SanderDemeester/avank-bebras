
package models.user;

import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Teacher extends SuperUser{

    /**
     * The constructor of teacher.
     */
    public Teacher(){

    }

    

    public void scheduleUnrestrictedCompetition(){

    }

    /**
     * @param regex A regex for filtering.
     * Apply's a seach filter for the teacher to Filter through all students in the System
     */
    public void searchStudents(String regex){
        //TODO: Need to add some filtering system
    }


    /**
     * @return A view to manageClassGroups.
     */
    public Result manageClasses(){
        return null;
    }

    /**
     * @return A view to manageCompetitions.
     */
    public Result manageCompetitions(){
        return null;
    }

}
