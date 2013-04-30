package models.dbentities;

import models.management.ManageableModel;
import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Question set entity.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsets")

public class QuestionSetModel extends ManageableModel {

    private static final long serialVersionUID = 4L;

    @Id
    public String id;

    @Required
    public String level;

    public boolean active;

    @Required
    public String name;

    public String contid;

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public String[] getFieldValues() {
        String[] result = {name, level, Boolean.toString(active)};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    public String getID() {
        return id;
    }

}
