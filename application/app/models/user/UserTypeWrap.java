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
	
	public UserTypeWrap(){

	}

	@Override
	public Map<String, String> options() {
		LinkedHashMap<String, String> ret_opts = new LinkedHashMap<String, String>();
		for(UserType ut : UserType.values()){
			ret_opts.put(ut.toString(), EMessages.get("user." + ut.toString()));
		}
		return ret_opts;
	}
}
