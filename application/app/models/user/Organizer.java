
package models.user;

import java.util.Set;

import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Organizer extends SuperUser{

    public Organizer(){

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

}
