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
import com.avaje.ebean.Ebean;

/**
 * @author Sander Demeester
 * @author Jens N. Rammant
 */

public class Teacher extends SuperUser{

    public Teacher(UserModel data) {
        super(data, UserType.TEACHER);
        ROLES.add(Role.MANAGECONTESTS);
        ROLES.add(Role.MANAGESCHOOLS);
        ROLES.add(Role.MANAGECLASSES);
        ROLES.add(Role.MIMIC);
    }


    /**
     * Queries the database for all Classes that this Teacher is main teacher of
     * @return a collection of classes that this Teacher is main teacher of
     * @throws PersistenceException when something goes wrong during the retrieval
     */
    public Collection<ClassGroup> getClasses() throws PersistenceException{
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
    
    /**
     * Checks whether the teacher is the pupil's current main teacher.
     * @param pupilID the id of the pupil to check
     * @return whether the teacher is the pupil's main teacher
     * @throws PersistenceException if something goes wrong with the db
     */
    public boolean isPupilsTeacher(User pupil) throws PersistenceException{
    	if(pupil==null||pupil.data==null)return false;
    	//Check if pupil has a classgroup
    	if(pupil.data.classgroup==null)return false;
    	//Retrieve all classes of the Teacher
    	Collection<ClassGroup> classes = this.getClasses();
    	classes.addAll(this.getHelpClasses());
    	//Retrieve all IDs
    	ArrayList<Integer> classIDs = new ArrayList<Integer>();
    	for(ClassGroup cg : classes){
    		classIDs.add(cg.id);
    	}
    	//Check if pupil's class is in the list
    	if(!classIDs.contains(pupil.data.classgroup))return false;
    	//Check if classgroup hasn't expired yet
    	ClassGroup pupilClass = Ebean.find(ClassGroup.class, pupil.data.classgroup);
    	if(pupilClass==null)return false; //Just to be sure. It is possible the class record got deleted.
    	return pupilClass.isActive();
    }
    
    public boolean isPupilsTeacher(String pupilID){
    	//Retrieve the pupil record
    	UserModel pupil = Ebean.find(UserModel.class, pupilID);
    	return this.isPupilsTeacher(new Independent(pupil));
    }
    
    @Override 
    public boolean canMimic(User user){
    	//Teacher can mimic pupils that have one of their classes as main class
    	//Check if pupil
    	if(user==null||user.data==null||
    			(user.data.type!=UserType.PUPIL_OR_INDEP))return false;
    	return isPupilsTeacher(user);
    }

}
