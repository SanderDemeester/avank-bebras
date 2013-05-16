/**
 *
 */
package controllers.util;

import java.util.HashMap;

import play.Play;

import models.user.Gender;

/**
 * @author Jens N. Rammant
 *
 */
public class GenderParser {

    private static HashMap<String,Gender> stringToGender;

    static{
        stringToGender = new HashMap<String,Gender>();
        String man = Play.application().configuration().getString("gender.male");
        String female = Play.application().configuration().getString("gender.female");
        String other = Play.application().configuration().getString("gender.other");

        for(String st : man.split(" +")){
            if(st!=null&&!st.trim().isEmpty())
                stringToGender.put(st.toUpperCase(), Gender.Male);
        }
        for(String st : female.split(" +")){
            if(st!=null&&!st.trim().isEmpty())
                stringToGender.put(st.toUpperCase(), Gender.Female);
        }
        for(String st : other.split(" +")){
            if(st!=null&&!st.trim().isEmpty())
                stringToGender.put(st.toUpperCase(), Gender.Other);
        }
    }

    public static Gender parseString(String st){
        if(st==null)return null;
        return stringToGender.get(st.toUpperCase());
    }
}
