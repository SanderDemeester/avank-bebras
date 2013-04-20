package models.dbentities;

import com.avaje.ebean.validation.NotNull;
import models.management.Editable;
import models.management.ManageableModel;
import models.question.Server;
import play.data.validation.Constraints.Required;

import javax.persistence.*;


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

    @ManyToOne
    @NotNull
    @JoinColumn(name="contid")
    public CompetitionModel contest;

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
