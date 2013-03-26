
package models.user;

import models.competition.Competition;
import models.dbentities.UserModel;

/**
 * @author Sander Demeester
 */
public class Pupil extends Independent{

    public Pupil(UserModel data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	//We first need a competition object.
	
	/**
     *
     * @param competition
     * @return returns if adding competition was succesfull.
     */
    public boolean addCompetition(Competition competition){
        return false;
    }
}
