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
	
	
	

}
