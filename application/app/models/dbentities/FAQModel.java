/**
 *
 */
package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.Editable;
import models.management.ManageableModel;
import play.data.validation.Constraints;

/**
 * @author Jens N. Rammant
 * Class for FAQ db entity
 */
@Entity
@Table(name="faq")
public class FAQModel extends ManageableModel {
    private static final long serialVersionUID = 2L;
    /**
     * ID of the FAQ
     */
    @Id
    @Editable
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_id_seq")
    public int id;
    /**
     * Actual question
     */
    @Constraints.Required
    @Editable
    public String name;
    /**
     * Language of the FAQ
     */
    @Constraints.Required
    @Editable
    public String language;
    /**
     * Answer
     */
    @Constraints.Required
    @Editable(hiddenInList=true)
    public String content;

    @Override
    public String[] getFieldValues() {
        String[] res = {Integer.toString(id),name,language};
        return res;
    }
    @Override
    public String getID() {
        return Integer.toString(id);
    }
}
