
package models.user;

import java.util.Set;

import javax.persistence.Entity;

import controllers.user.Type;

import play.mvc.Result;

/**
 * @author Sander Demeester
 */
@Entity
public class Organizer extends SuperUser{

    public Organizer(UserID id, Type loginType, String name){
    	super(id,loginType,name);
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

	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
