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
 *
 */
//TODO: write test
@Entity
@Table(name="ClassPupil")
public class ClassPupil extends Model {
	@Id
	public String classid;
	@Id
	public String indid;
}
