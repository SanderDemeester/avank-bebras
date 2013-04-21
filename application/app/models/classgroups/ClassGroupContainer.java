/**
 * 
 */
package models.classgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.dbentities.ClassGroup;
import models.dbentities.UserModel;
import models.user.AuthenticationManager;

/**
 * @author Jens N. Rammant
 * container class for a ClassGroup object and related UserModel objects. 
 */
public class ClassGroupContainer {

	private ClassGroup classGroup=null;
	private boolean isCGValid=false;
	private String cgMessage="";
	private boolean isCGNew=true;
	
	private List<PupilRecordTriplet> newPupils;
	private List<PupilRecordTriplet> existingPupils;
	
	
	public ClassGroupContainer(){
		this.classGroup=null;
		this.newPupils = new ArrayList<PupilRecordTriplet>();
		this.existingPupils = new ArrayList<PupilRecordTriplet>();
	}
	
	public void setClassGroup(ClassGroup cg, boolean isValid, String message,boolean isNew){
		this.classGroup = cg;
		this.isCGValid = isValid;
		this.cgMessage = message;
		this.isCGNew = isNew;
	}
	
	public void appendCGMessage(String st){
		this.cgMessage = this.cgMessage +"\n"+st;
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
	 * @return the isCGNew
	 */
	public boolean isCGNew() {
		return isCGNew;
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
	 * For all the valid records in the class, check if they will be able to be saved. If not, set isValid to false
	 * and add a message.
	 * @throws PersistenceException when something goes wrong with the db
	 */
	public void validify() throws PersistenceException{
		//TODO
	}

	/**
	 * Saves all the containers in a transaction
	 * @return true if saving was successful
	 */
	public static boolean save(Collection<ClassGroupContainer> coll){
		//TODO comments
		boolean res = true;
		Ebean.beginTransaction();
		try{
			for(ClassGroupContainer cgc : coll){
				if(cgc.isCGValid()){
					if(cgc.isCGNew){
						UserModel u = AuthenticationManager.getInstance().getUser().data;
						cgc.classGroup.teacherid=u.id;
						cgc.classGroup.save();
					}
					for(PupilRecordTriplet prt : cgc.getNewPupils()){
						if(prt.isValid){
							prepareNewPupil(prt.user,cgc.getClassGroup());
							//prt.user.save();
						}
					}
					for(PupilRecordTriplet prt : cgc.getExistingPupils()){
						if(prt.isValid){
							updateExistingPupil(prt.user, cgc.getClassGroup());
						}
					}
				}
			}
			Ebean.commitTransaction();
		}catch(PersistenceException pe){
			pe.printStackTrace();
			Ebean.rollbackTransaction();
			res=false;
		}finally{
			Ebean.endTransaction();
		}
		return res;
	}
	/**
	 * Hashes the password, generates username, sets registrationdate, sets class
	 * @param model
	 */
	private static void prepareNewPupil(UserModel model,ClassGroup cg){
		//TODO
	}
	
	/**
	 * Updates the existing pupil to link to a new class, but makes
	 * sure to remember the old linking in a ClassPupil relationship
	 * @param model UserModel to save
	 * @param cg ClassGroup to link
	 */
	private static void updateExistingPupil(UserModel model, ClassGroup cg){
		//TODO
	}
	
	/**
	 * 
	 * @author Jens N. Rammant
	 *	Links a usermodel with a boolean that says if it's valid and a message (that's already been localized)
	 * that can be shown to users
	 */
	public static class PupilRecordTriplet{
		public UserModel user;
		public boolean isValid;
		public String message;
	}
	
	
}
