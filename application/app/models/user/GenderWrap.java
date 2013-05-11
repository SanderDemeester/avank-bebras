package models.user;

import java.util.LinkedHashMap;
import java.util.Map;

import models.EMessages;
import models.management.Listable;


/*
 * WRAPPERCLASS FOR GENDER
 * AUTHOR: thomas m
 * 
 */

public class GenderWrap implements Listable {
	
	public static Gender getUserType(String g) {
		if(g.equals("Male") || g.equals("Man")) {
			return Gender.Male;
		} else if(g.equals("Female") || g.equals("Vrouw")) {
			return Gender.Female;
		} else {
			return Gender.Other;
		}
	}

	@Override
	public Map<String, String> options() {
		LinkedHashMap<String, String> ret_opts = new LinkedHashMap<String, String>();
		for(Gender g : Gender.values()){
			ret_opts.put(EMessages.get("user." + g.toString()), EMessages.get("user." + g.toString()));
		}
		return ret_opts;
	}
	
}
