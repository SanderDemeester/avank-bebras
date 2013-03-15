
package controllers.user;

import java.util.ArrayList;
import java.util.List;

import models.user.ClassGroup;

import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Independent extends User{
	
	private List<String> previousClassList;
	
	public Independent(){
		super(); //abstract class constructor could init some values
		previousClassList = new ArrayList<String>();
	}
	
	/**
	 * Constructor for Independent-user. 
	 * This constructor makes a copy of all the information
	 * provided in independent.
	 * @param independent
	 */
	public Independent(Independent independent){
		
	}

	/**
	 * Show specific landingpage for user Independent.
	 */
	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Add an old class
	 * @param oldClass
	 */
	public void addPreviousClass(String oldClass){
		previousClassList.add(oldClass);
	}
	
	/**
	 * Add a class to Independent user.
	 * @param classGroup
	 */
	public void addCurrentClass(ClassGroup classGroup){
		
	}
	
	/**
	 * 
	 * @return Get currentClass.
	 */
	public ClassGroup getCurrentClass(){
		return null;
	}
	

}
