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
	
	public GenderWrap() {

	}

	@Override
	public Map<String, String> options() {
		LinkedHashMap<String, String> ret_opts = new LinkedHashMap<String, String>();
		for(Gender g : Gender.values()){
			ret_opts.put(g.toString(), EMessages.get("user." + g.toString()));
		}
		return ret_opts;
	}
	
}
