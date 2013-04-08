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
import play.data.validation.Constraints;

/**
 * @author Jens N. Rammant
 *
 */
@Entity
@Table(name="faq")
public class FAQModel extends ManageableModel {
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_id_seq")
	public int id;
	@Constraints.Required
	public String name;
	@Constraints.Required
	public String language;
	@Constraints.Required
	public String content;
	
	@Override
	public String[] getFieldValues() {
		String[] res = {Integer.toString(id),language,name};
		return res;
	}
	@Override
	public String getID() {
		return Integer.toString(id);
	}
	public String getName() {
		return "FAQ";
	}
}
