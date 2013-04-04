/**
 * 
 */
package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.Manageable;

import play.db.ebean.Model;

/**
 * @author Jens N. Rammant
 *
 */
@Entity
@Table(name="faq")
public class FAQModel extends Model implements Manageable{

	@Id
	public int id;
	public String name;
	public String language;
	public String content;
	
	@Override
	public String[] getFieldValues() {
		String[] res = {language,name};
		return res;
	}
	@Override
	public String getID() {
		return Integer.toString(id);
	}
}
