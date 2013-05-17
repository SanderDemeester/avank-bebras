package models.user;

import java.util.LinkedHashMap;
import java.util.Map;

import models.EMessages;
import models.management.Listable;

/**
 * This class represents a wrapper class for the class gender.
 * This is used for the list view in the DMTV.
 * @author Thomas Mortier
 */
public class GenderWrap implements Listable {
	
    public GenderWrap() {

    }

    /**
     * Returns a map for the translation of different genders.
     * This is used in the listview in the DMTV.
     * @return a map for the translation of different genders
     */
    @Override
    public Map<String, String> options() {
        LinkedHashMap<String, String> ret_opts = new LinkedHashMap<String, String>();
        for(Gender g : Gender.values()) {
            ret_opts.put(g.toString(), EMessages.get("user." + g.toString()));
        }
        return ret_opts;
    }
}
