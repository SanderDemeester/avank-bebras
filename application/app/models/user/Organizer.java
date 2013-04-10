
package models.user;

import java.util.Set;

import models.dbentities.UserModel;

import play.mvc.Content;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */

public class Organizer extends SuperUser{

    static {
        ROLES.add(Role.MANAGEQUESTIONS);
    }

    protected Organizer(UserModel data, UserType type) {
        super(data, type);
    }

    public Organizer(UserModel data) {
        this(data, UserType.ORGANIZER);
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
    public void mimicPupil(Independent user){

    }

    /**
     *
     * @return return a list of all teachers.
     */
    public Set<Teacher> listTeachers(){
        return null;
    }

    /*
     * Returns the Question Management Page
     * @return Question Management Page
     */
    public Result manageQuestions(){
        return null;
    }

    /*
     * Returns the Competition Management Page
     * @return Competition Management Page
     */
    public Result manageCompetitions(){
        return null;
    }

    @Override
    public Content getLandingPage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result showStatistics() {
        // TODO Auto-generated method stub
        return null;
    }
}
