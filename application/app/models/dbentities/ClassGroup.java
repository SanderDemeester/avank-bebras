package models.dbentities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.ManageableModel;

/**
 * This class contains information that models a group of pupils
 * @author Sander Demeester
 */
@Entity
@Table(name="Classes")
public class ClassGroup extends ManageableModel{
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_id_seq")
    public int id;
    public String name;
    public Date expdate;
    public int schoolid;
    public String teacherid;
    public String level;

    /**
     * Constructor for ClassGroup.
     */
    public ClassGroup(){

    }

    public String getName(){
        return this.name;
    }

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
		String[] res = {Integer.toString(id),name,Integer.toString(schoolid),level,expdate.toString(),Boolean.toString(isActive())};
		return res;
	}

}
