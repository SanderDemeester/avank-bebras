package models.dbentities;

import models.management.ManageableModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Database entity for question set questions.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsetquestions")
public class QuestionSetQuestion extends ManageableModel {

    public String qsid;

    public String qid;

    public String difficulty;

    // TODO rekening houden met combined id

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {qsid, qid, difficulty};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return qid;
    }

}
