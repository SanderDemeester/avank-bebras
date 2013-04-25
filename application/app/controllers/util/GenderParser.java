/**
 * 
 */
package controllers.util;

import java.util.HashMap;

import models.user.Gender;

/**
 * @author Jens N. Rammant
 *
 */
public class GenderParser {
	
	private static HashMap<String,Gender> stringToGender;
	
	static{
		stringToGender = new HashMap<String,Gender>();
		stringToGender.put("M", Gender.Male);
		stringToGender.put("MALE", Gender.Male);
		stringToGender.put("MAN", Gender.Male);
		stringToGender.put("HOMME", Gender.Male);
		stringToGender.put("F", Gender.Female);
		stringToGender.put("FEMALE", Gender.Female);
		stringToGender.put("FEMME", Gender.Female);
		stringToGender.put("FRAU", Gender.Female); //TODO check if correct
		stringToGender.put("VROUW", Gender.Female);
		stringToGender.put("O", Gender.Other);
		stringToGender.put("ANDERS", Gender.Other);
		stringToGender.put("OTHER", Gender.Other);
		//TODO FRENCH & GERMAN
	}

	public static Gender parseString(String st){
		if(st==null)return null;
		return stringToGender.get(st.toUpperCase());
	}
}
