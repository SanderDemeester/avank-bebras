/**
 * 
 */
package models.classgroups;

import java.util.ArrayList;
import java.util.List;

import models.dbentities.ClassGroup;
import models.dbentities.UserModel;

/**
 * @author Jens N. Rammant
 * container class for a ClassGroup object and related UserModel objects. 
 */
public class ClassGroupContainer {

	private ClassGroup classGroup;
	private boolean isCGValid;
	private String cgMessage;
	
	private List<PupilRecordTriplet> newPupils;
	private List<PupilRecordTriplet> existingPupils;
	
	
	public ClassGroupContainer(){
		this.classGroup=null;
		this.newPupils = new ArrayList<PupilRecordTriplet>();
		this.existingPupils = new ArrayList<PupilRecordTriplet>();
	}
	
	public void setClassGroup(ClassGroup cg, boolean isValid, String message){
		this.classGroup = cg;
		this.isCGValid = isValid;
		this.cgMessage = message;
	}
	
	public void addNewPupil(PupilRecordTriplet prt){
		this.newPupils.add(prt);
	}
	
	public void addExistingPupil(PupilRecordTriplet prt){
		this.existingPupils.add(prt);
	}	
	
	/**
	 * @return the classGroup
	 */
	public ClassGroup getClassGroup() {
		return classGroup;
	}

	/**
	 * @return the isCGValid
	 */
	public boolean isCGValid() {
		return isCGValid;
	}

	/**
	 * @return the cgMessage
	 */
	public String getCgMessage() {
		return cgMessage;
	}

	/**
	 * @return the newPupils
	 */
	public List<PupilRecordTriplet> getNewPupils() {
		List<PupilRecordTriplet> res = new ArrayList<PupilRecordTriplet>();
		res.addAll(newPupils);
		return res;
	}

	/**
	 * @return the existingPupils
	 */
	public List<PupilRecordTriplet> getExistingPupils() {
		List<PupilRecordTriplet> res = new ArrayList<PupilRecordTriplet>();
		res.addAll(existingPupils);
		return res;
	}

	/**
	 * Saves all the contents
	 * @return true if saving was successful
	 */
	public boolean save(){
		//TODO
		return false;
	}
	
	/**
	 * 
	 * @author Jens N. Rammant
	 *	Links a usermodel with a boolean that says if it's valid and a message (that's already been localized)
	 * that can be shown to users
	 */
	public class PupilRecordTriplet{
		public UserModel user;
		public boolean isValid;
		public String message;
	}
	
	
}
