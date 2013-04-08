/**
 * 
 */
package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

/**
 * @author Jens N. Rammant
 * TODO change to Ruben's standard
 * TODO test
 */
@Entity
@Table(name="Schools")
public class SchoolModel extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	public String id;
	public String name;
	public String address;
	public String orig;
	/**
	 * @param id
	 * @param name
	 * @param address
	 * @param orig
	 */
	public SchoolModel(String id, String name, String address, String orig) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.orig = orig;
	}
	
	public SchoolModel(){}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
	
	
	
	
	

}
