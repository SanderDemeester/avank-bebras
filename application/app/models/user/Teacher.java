
package models.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.PersistenceException;

import models.dbentities.ClassGroup;
import models.dbentities.HelpTeacher;
import models.dbentities.SchoolModel;
import models.dbentities.UserModel;
import play.mvc.Result;
import play.mvc.Content;
import com.avaje.ebean.Ebean;

/**
 * @author Sander Demeester
 * @author Jens N. Rammant
 */

public class Teacher extends SuperUser{

    public Teacher(UserModel data) {
        super(data, UserType.TEACHER);
    }

    public void scheduleUnrestrictedCompetition(){

    }

    /**
     * @param regex A regex for filtering.
     * Applys a seach filter for the teacher to Filter through all students in the System
     */
    public void searchStudents(String regex){
        //TODO: Need to add some filtering system
    }


    /**
     * @return A view to manageCompetitions.
     */
    public Result manageCompetitions(){
        return null;
    }
    /*
     * Creates the personalized landing page for this instance of Teacher.
     * @return Personalized landing page for this instance of teacher
     */
    public Content getLandingPage(){
        //TODO
        return null;
    }

    @Override
    public Result showStatistics() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Queries the database for all Classes that this Teacher is main teacher of
     * @return a collection of classes that this Teacher is main teacher of
     * @throws PersistenceException when something goes wrong during the retrieval
     */
    public Collection<ClassGroup> getClasses() throws PersistenceException{
    	//TODO safety
        java.util.List<ClassGroup> res = Ebean.find(ClassGroup.class).where()
                .eq("teacherid", this.data.id).findList();

        return res;
    }
    
    /**
     * Queries the database for all Schools the Teacher either created or
     * is associated with via a class he teaches/taught.
     * @return a list of schools the teacher is/was associated with
     * @throws PersistenceException when something goes wrong during the retrieval
     */
    public Collection<SchoolModel> getSchools() throws PersistenceException{
    	//Retrieve all the school the teacher created
    	Set<SchoolModel> res = new HashSet<SchoolModel>();
    			res.addAll(Ebean.find(SchoolModel.class).where()
    			.eq("orig", this.data.id).findList());
    	//Retrieve all the schoolids from classes the Teacher is associated with
    	HashSet<Integer> schoolIDs = new HashSet<Integer>();
    	for(ClassGroup cg : this.getClasses()){
    		schoolIDs.add(cg.schoolid);
    	}
    	//Retrieve all the SchoolModels from those ids
    	for(Integer s : schoolIDs){
    		SchoolModel m = Ebean.find(SchoolModel.class).where()
    				.eq("id", s).findUnique();
    		if(m!=null)res.add(m);
    	}
    	return res;
    }
    
    /**
     * 
     * @return a list of classes the Teacher is help teacher for
     * @throws PersistenceException when something goes wrong with the db
     */
    public Collection<ClassGroup> getHelpClasses() throws PersistenceException{
    	//TODO write jUnit
    	ArrayList<ClassGroup> res = new ArrayList<ClassGroup>();
    	
    	Collection<HelpTeacher> ht = Ebean.find(HelpTeacher.class).where().eq("teacherid", this.data.id).findList();
    	for(HelpTeacher h : ht){
    		ClassGroup cg = Ebean.find(ClassGroup.class).where().eq("id",h.classid).findUnique();
    		if(cg==null)throw new PersistenceException("Could not find ClassGroup with that id.");
    		res.add(cg);
    	}
    	return res;
    }

}
