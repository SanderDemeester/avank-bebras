/**
 * 
 */
package controllers.classgroups;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import models.EMessages;
import models.classgroups.ClassGroupContainer;
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
	 * @param existingClassId an existing class to which the pupils have to be added. If null, ignore
	 * @return ClassGroups & pupils
	 */
	public static List<ClassGroupContainer> listToClassGroup(List<List<String>> list, Integer existingClassId){
		//TODO
		return null;
	}
	
	/**
	 * Converts a list (from XLSX Importer) to a saveable format
	 * @param list list to be converted
	 * @return ClassGroups & pupils
	 */
	public static List<ClassGroupContainer> listToClassGroup(List<List<String>> list){
		return listToClassGroup(list, null);
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
	
}
