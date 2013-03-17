
package controllers.user;

import org.codehaus.jackson.JsonNode;
import play.mvc.Result;

/**
 * This class contains all the logic for chaning/updating a user person information.
 * @author Sander Demeester
**/
public class PersonalInformationEditor {

    public PersonalInformationEditor(){

    }

    /**
     *
     * @return returns a Result view to show some person information.
     */
    public Result showPersonalInformation(){
        return null;
    }

    /**
     *
     * @param json contains the information that should be changed.
     * @return returns a Result view to change some personal information.
     */
    public Result changePersonalInformation(JsonNode json){
        return null;
    }
}
