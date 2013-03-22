
package models.user;

import javax.persistence.Entity;

import controllers.user.Type;

import models.competition.Competition;

/**
 * @author Sander Demeester
 */
@Entity
public class Pupil extends Independent{

    //We first need a competition object.

    public Pupil(Independent independent){
        super(independent);
    }



	public Pupil(UserID userID, Type logintType, String name) {
		// TODO Auto-generated constructor stub
		super(userID,logintType,name);
	}



	/**
     *
     * @param competition
     * @return returns if adding competition was succesfull.
     */
    public boolean addCompetition(Competition competition){
        return false;
    }
}
