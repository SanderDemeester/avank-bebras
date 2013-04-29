/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;



import models.dbentities.ClassGroup;
import models.dbentities.UserModel;

/**
 * @author Jens N. Rammant
 * container class for a ClassGroup object and related UserModel objects. 
 */
public class ClassGroupContainer {

	private ClassGroup newClassGroup;
	private List<UserModel> newPupils;
	private List<UserModel> existingPupils;
	private Integer existingClassID;
	/**
	 * 
	 */
	public ClassGroupContainer() {
		this(null);
	}
	
	public ClassGroupContainer(Integer existingClassID){
		this.newClassGroup=null;
		this.existingClassID=existingClassID;
		this.newPupils = new ArrayList<UserModel>();
		this.existingPupils = new ArrayList<UserModel>();
	}
	
	/**
	 * Saves all the contents
	 * @return true if saving was successful
	 */
	public boolean save(){
		//TODO
		return false;
	}
	
	
}
