
package models.user;

import java.util.ArrayList;
import java.util.List;

import controllers.user.Type;


import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Independent extends User{

    private List<String> previousClassList;

    public Independent(UserID id, Type loginType, String name){
        super(id,loginType,name); //abstract class constructor could init some values
        previousClassList = new ArrayList<String>();
    }

    /**
     * Constructor for Independent-user.
     * This constructor makes a copy of all the information
     * provided in independent.
     * @param independent
     */
    public Independent(Independent independent){
    	//TODO: independent need getters setters for this information.
    	super(null,Type.INDEPENDENT,"");
    }

    /**
     * Add an old class
     * @param oldClass
     */
    public void addPreviousClass(String oldClass){
        previousClassList.add(oldClass);
    }

    /**
     * Add a class to Independent user.
     * @param classGroup
     */
    public void addCurrentClass(ClassGroup classGroup){

    }

    /**
     *
     * @return Get currentClass.
     */
    public ClassGroup getCurrentClass(){
        return null;
    }


}
