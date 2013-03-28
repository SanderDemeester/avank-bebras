package models.dbentities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

/**
 * This class contains information that models a group of pupils
 * @author Sander Demeester
 */
//TODO: write test
@Entity
@Table(name="Classes")
public class ClassGroup extends Model{
	
	@Id
	public String id;
	public String name;
	public Date expdate;
	public String schoolid;
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
    	return this.id;
    }

}
