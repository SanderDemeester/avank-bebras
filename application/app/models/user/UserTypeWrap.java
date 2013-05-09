package models.user;

import java.util.LinkedHashMap;
import java.util.Map;

import models.EMessages;
import models.management.Listable;

/*
 * WRAPPERCLASS FOR USERTYPE
 * AUTHOR: thomas m
 * 
 */


public class UserTypeWrap implements Listable {
	
	public static UserType getUserType(String t) {
		if(t.equals("Administrator")) {
			return UserType.ADMINISTRATOR;
		} else if(t.equals("Organizer") || t.equals("Organisator")) {
			return UserType.ORGANIZER;
		} else if(t.equals("Teacher") || t.equals("Leerkracht")) {
			return UserType.TEACHER;
		} else if(t.equals("Author") || t.equals("Auteur")) {
			return UserType.AUTHOR;
		} else if(t.equals("Student") || t.equals("Student")) {
			return UserType.PUPIL_OR_INDEP;
		} else {
			return UserType.ANON;
		}	
	}

	@Override
	public Map<String, String> options() {
		LinkedHashMap<String, String> ret_opts = new LinkedHashMap<String, String>();
		for(UserType ut : UserType.values()){
			ret_opts.put(EMessages.get("user." + ut.toString()), EMessages.get("user." + ut.toString()));
		}
		return ret_opts;
	};

}
