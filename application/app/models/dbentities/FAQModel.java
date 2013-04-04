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
@Entity
@Table(name="faq")
public class FAQModel extends Model {

	@Id
	public int id;
	public String name;
	public String language;
	public String content;
}
