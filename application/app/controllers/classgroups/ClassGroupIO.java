/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import models.EMessages;
import models.classgroups.ClassGroupContainer;
import models.classgroups.ClassGroupContainer.PupilRecordTriplet;
import models.dbentities.ClassGroup;
import models.dbentities.UserModel;

import com.avaje.ebean.Ebean;

import controllers.util.DateFormatter;

/**
 * @author Jens N. Rammant
 *	A helper class for the reading and writing of classgroups.
 *	Works together with {@link controllers.util.XLSXImporter}
 * TODO tests
 */
public class ClassGroupIO {
	
	private static List<String> classHeader;
	private static List<String> pupilHeader;
	
	static{
		classHeader = new ArrayList<String>();
		classHeader.add("#");
		classHeader.add(EMessages.get("class.name"));
		classHeader.add(EMessages.get("class.expdate"));
		classHeader.add(EMessages.get("class.level"));
		classHeader.add(EMessages.get("class.school"));
		
		pupilHeader = new ArrayList<String>();
		pupilHeader.add("#");
		pupilHeader.add(EMessages.get("classes.pupil.form.id"));
		pupilHeader.add(EMessages.get("classes.pupil.form.name"));
		pupilHeader.add(EMessages.get("classes.pupil.form.birthdate"));
		pupilHeader.add(EMessages.get("classes.pupil.form.gender"));
		pupilHeader.add(EMessages.get("classes.pupil.form.preflanguage"));
		pupilHeader.add(EMessages.get("classes.pupil.form.password"));
		pupilHeader.add(EMessages.get("classes.pupil.form.email"));
		
	}

	/**
	 * Turns a classgroup and its contents into a format writeable by XLSXImporter
	 * @param classID id of the class
	 * @return the classgroup and its contents in a format writeable by XLSXImporter, or null if db failed
	 */
	public static List<List<String>> fullClassgroupToList(int classID){
		try{
			List<List<String>> res = new ArrayList<List<String>>();
			res.add(classHeader);
			ClassGroup cg = Ebean.find(ClassGroup.class,classID);
			if(cg==null)return null;
			res.add(classgroupToList(cg));
			
			res.add(pupilHeader);
			List<UserModel> pupils = cg.getPupils(ClassGroup.PupilSet.ALL);
			for(UserModel um : pupils){
				res.add(userModelToList(um));
			}
			
			return res;
		}catch(PersistenceException pe){
			return null;
		}
	}
	
	/**
	 * Converts a list (from XLSX Importer) to a saveable format
	 * @param list list to be converted
	 * @param existingClassId an existing class to which the pupils have to be added.
	 * @return ClassGroup & pupils, null if something goes wrong with the db
	 */
	public static ClassGroupContainer listToClassGroup(List<List<String>> list, int existingClassId){
		try{
			//Try to add the existing class to the container
			ClassGroupContainer res = new ClassGroupContainer();
			ClassGroup cg = Ebean.find(ClassGroup.class,existingClassId);
			String message = cg!=null?"":EMessages.get("classes.import.classnotexist");
			res.setClassGroup(cg, cg!=null, message, false);
			//Iterate over all the records
			for(int i=0;i<list.size();i++){
				List<String> record = list.get(i);
				//CLASS records are not read, but an error is added
				if(record.get(0).equalsIgnoreCase("CLASS")){
					res.appendCGMessage(EMessages.get("classes.import.classrecordwhileaddingtoexisting"));
				}
				else if(record.get(0).equalsIgnoreCase("PUPIL")){
					//Parse the pupil record
					UserModel parsed = parseUserModel(record);
					PupilRecordTriplet prt = parsedUserModelToTriplet(parsed);
					if(parsed.id!=null){
						//If id is mentioned, 
						//add to the existing Pupil list (even if it doesn't exist, but isValid
						//is false then, so it won't be saved.
						res.addExistingPupil(prt);
					}
					//If no id is mentioned, add to the new pupil list
					else res.addNewPupil(prt);
				}
			}
			//Check some other constraints
			res.validify();
			return res;
		//If something goes wrong, return null	
		}catch(PersistenceException pe){
			return null;
		}
	}
	
	/**
	 * Converts a list (from XLSX Importer) to a saveable format
	 * @param list list to be converted
	 * @return ClassGroups & pupils
	 */
	public static List<ClassGroupContainer> listToClassGroup(List<List<String>> list){
		//TODO
		return null;
	}
	
	
	/**
	 * 
	 * @param cg ClassGroup
	 * @return the contents of the ClassGroup object in a format that can be added to the list
	 */
	private static List<String> classgroupToList(ClassGroup cg){
		ArrayList<String> res = new ArrayList<String>();
		res.add("CLASS");
		res.add(cg.name);
		res.add(DateFormatter.formatDate(cg.expdate));
		res.add(cg.level);
		res.add(Integer.toString(cg.schoolid));
		return res;
	}
	
	/**
	 * 
	 * @param um UserModel
	 * @return the contents of the UserModel object in a format that can be added to the list
	 */
	private static List<String> userModelToList(UserModel um){
		ArrayList<String> res = new ArrayList<String>();
		res.add("PUPIL");
		res.add(um.id);
		res.add(um.name);
		res.add(DateFormatter.formatDate(um.birthdate));
		res.add(um.gender.toString());
		res.add(um.preflanguage);
		res.add("");
		res.add(um.email);		
		return res;
	}
	
	/**
	 * 
	 * @param toParse List to parse into UserModel
	 * @return a UserModel filled with the data in the list
	 */
	private static UserModel parseUserModel(List<String> toParse){
		//TODO
		return null;
	}
	
	/**
	 * 
	 * @param parsed UserModel to put in the record
	 * @return a PupilRecordTriplet that fits the parsed data
	 */
	private static PupilRecordTriplet parsedUserModelToTriplet(UserModel parsed){
		PupilRecordTriplet prt = new PupilRecordTriplet();
		prt.user=parsed;
		prt.message="";
		prt.isValid=true;
		//If an id is mentioned, try to add the existing userdata
		if(parsed.id!=null){
			UserModel existing = Ebean.find(UserModel.class, parsed.id);
			//If the userdata doesn't exist, add error message and show the parsed record
			if(existing==null){
				prt.isValid=false;
				prt.message=EMessages.get("classes.import.usernotexist");
			}
			//Else add the existing userdata
			else{
				prt.user=existing;
			}
		}
		return prt;
	}
	
}
