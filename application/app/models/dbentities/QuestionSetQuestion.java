package models.dbentities;

import models.data.Difficulty;
import models.management.ManageableModel;
import javax.persistence.*;

/**
 * Database entity for question set questions.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsetquestions")
public class QuestionSetQuestion extends ManageableModel {

    private static final long serialVersionUID = 2L;

    /**
     * corresponding question set id
     */
    @ManyToOne
    @JoinColumn(name="qsid")
    public QuestionSetModel questionSet;

    /**
     * question id
     */
    @Id
    @ManyToOne
    @JoinColumn(name="qid")
    public int qid;

    /**
     * difficulty
     */
    @ManyToOne
    @JoinColumn(name="difficulty")
    public Difficulty difficulty;

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {"" + qid, questionSet.name, difficulty.name};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return Integer.toString(qid);
    }

}
