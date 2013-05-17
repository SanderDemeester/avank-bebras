/**
 *
 */
package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.ManageableModel;
import play.data.validation.Constraints.Required;

/**
 * @author Jens N. Rammant
 */
@Entity
@Table(name="Schools")
public class SchoolModel extends ManageableModel implements Comparable<SchoolModel>{

    /**
     * 
     */
    private static final long serialVersionUID = 2L;
    /**
     * ID of the school
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schools_id_seq")
    public int id;
    /**
     * Name of the school
     */
    @Required
    public String name;
    /**
     * Address of the school
     */
    @Required
    public String address;
    /**
     * ID of the user who created the school entity
     */
    public String orig;
    /**
     * Constructor
     * @param id ID of the school
     * @param name name of the school
     * @param address address of the school
     * @param orig ID of the user who created the school entity
     */
    public SchoolModel(int id, String name, String address, String orig) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.orig = orig;
    }
    
    /**
     * Empty constructor
     */
    public SchoolModel(){}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((orig == null) ? 0 : orig.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof SchoolModel))
            return false;
        SchoolModel other = (SchoolModel) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (orig == null) {
            if (other.orig != null)
                return false;
        } else if (!orig.equals(other.orig))
            return false;
        return true;
    }

    @Override
    public int compareTo(SchoolModel o) {
        int res = this.name.toLowerCase().compareTo(o.name.toLowerCase());
        if (res!=0) return res;
        res = this.address.toLowerCase().compareTo(o.address.toLowerCase());
        if(res!=0) return res;
        return Integer.compare(this.id, o.id);
    }

    @Override
    public String[] getFieldValues() {
        String[] res = {
                getID(),
                name,
                address
        };
        return res;
    }

    @Override
    public String getID() {
        return Integer.toString(id);
    }

}
