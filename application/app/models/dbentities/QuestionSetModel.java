package models.dbentities;

import models.management.Manageable;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;


/**
 * Question set entity.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsets")
public class QuestionSetModel extends Model implements Manageable {

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
    @Override
    public String[] getFieldValues() {
        String[] result = {name};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Returns the name of the object.
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }
}
