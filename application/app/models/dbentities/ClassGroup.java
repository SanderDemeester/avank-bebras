package models.dbentities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

/**
 * This class contains information that models a group of pupils
 * @author Sander Demeester
 */
@Entity
@Table(name="Classes")
public class ClassGroup extends Model{
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_id_seq")
    public int id;
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

    public int getID(){
        return this.id;
    }

}
