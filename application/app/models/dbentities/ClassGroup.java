package models.dbentities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;

import models.management.ManageableModel;
import models.user.Teacher;
import models.user.UserType;
import play.data.validation.Constraints;
import play.data.format.Formats;

/**
 * This class contains information that models a group of pupils
 * @author Sander Demeester
 */
@Entity
@Table(name="Classes")
public class ClassGroup extends ManageableModel{
    private static final long serialVersionUID = 4L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_id_seq")
    public int id;
    /**
     * name
     */
    @Constraints.Required
    public String name;
    /**
     * expiration date
     */
    @Constraints.Required
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date expdate;
    /**
     * school id
     */
    @Constraints.Required
    public int schoolid;
    /**
     * teacher id
     */
    public String teacherid;
    /**
     * level
     */
    @Constraints.Required
    public String level;

    /**
     * Constructor for ClassGroup.
     */
    public ClassGroup(){

    }

    /**
     * @return the name
     */
    public String getName(){
        return this.name;
    }

    @Override
    public String getID(){
        return Integer.toString(this.id);
    }
    /**
     *
     * @return if the class is active. A class is active iff its experation date is not before today
     */
    public boolean isActive(){
        Calendar c = Calendar.getInstance();
        return this.isActive(truncateTime(c.getTime()));
    }

    /**
     * The extra argument can be used to make sure the status of multiple classes is compared against the same basis
     * @param d The date against which the expiration date has to be compared. Hours and smaller will be ignored
     * @return if the class is active. A class is active iff its experation date is not before d
     */
    public boolean isActive(Date d){
        Date expCopy = (Date)expdate.clone();
        Date dCopy = (Date)d.clone();

        expCopy = truncateTime(expCopy);
        dCopy = truncateTime(dCopy);

        return !expCopy.before(dCopy);
    }
    /**
     * Removes the hours, minutes, seconds and milliseconds from a Date
     * @param d the date to be truncated
     * @return the truncated date
     */
    private Date truncateTime(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    @Override
    public String[] getFieldValues() {
        String[] res = {Integer.toString(id),name,Integer.toString(schoolid),teacherid,level,getExpDate()};
        return res;
    }

    /**
     *
     * @return the expiration date in a compact string format
     */
    public String getExpDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(expdate);
    }

    /**
     *
     * @return the Teacher object that is linked to this classgroup
     * @throws PersistenceException if something goes wrong during the retrieval
     */
    public Teacher getTeacher() throws PersistenceException{
        UserModel data = Ebean.find(UserModel.class).where().eq("id", teacherid).findUnique();
        if(data==null)return null;
        if(data.type!=UserType.TEACHER)return null;
        return new Teacher(data);
    }

    /**
     *
     * @return the SchoolModel object that is linked to this classgroup
     * @throws PersistenceException if something goes wrong during the retrieval
     */
    public SchoolModel getSchool() throws PersistenceException{
        return Ebean.find(SchoolModel.class).where().eq("id",schoolid).findUnique();
    }

    /**
     *
     * @param ps which set of the pupils that should be returned
     * @return a list of all the pupils that are or were in this class
     * @throws PersistenceException
     */
    public List<UserModel> getPupils(PupilSet ps) throws PersistenceException{
        //Retrieve all the ClassPupil objects that are linked to this class & extract ids
        Collection<String> pupIDs = new ArrayList<String>();
        Collection<ClassPupil> cp = Ebean.find(ClassPupil.class).where().eq("classid", this.id).findList();
        for(ClassPupil c : cp)pupIDs.add(c.indid);

        Expression active = Expr.eq("classgroup", this.id); //Find all active students
        Expression nonActive = Expr.in("id", pupIDs); //Find all non-active students
        if(ps == PupilSet.ACTIVE)
            return Ebean.find(UserModel.class).where().add(active).findList();
        if(ps == PupilSet.NONACTIVE)
            return Ebean.find(UserModel.class).where().add(nonActive).findList();
        if(ps == PupilSet.ALL)
            return Ebean.find(UserModel.class).where().or(active, nonActive).findList(); //Find all
        return null;
    }

    /**
     *
     * pupil set type
     *
     */
    public enum PupilSet{
        /**
         * All
         */
        ALL,
        /**
         * Active
         */
        ACTIVE,
        /**
         * Non-active
         */
        NONACTIVE
    }

}
