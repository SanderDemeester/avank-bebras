
package controllers.user;

import models.competition.Competition;

/**
 * @author Sander Demeester
 */
public class Pupil extends Independent{

	//We first need a competition object.
	
	public Pupil(Independent independent){
		super(independent);
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
