package models.dbentities;

import models.data.Grade;
import models.management.ManageableModel;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionsets_id_seq")
    public int id;

    @Column(name="level")
    @ManyToOne
    @JoinColumn(name="level")
    public Grade grade;

    @Required
    public boolean active;

    @Required
    public String name;

    @ManyToOne
    @JoinColumn(name="contid")
    public CompetitionModel contest;

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public String[] getFieldValues() {
        String[] result = {name, grade.getName(), Boolean.toString(active)};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    public String getID() {
        return Integer.toString(id);
    }

}
