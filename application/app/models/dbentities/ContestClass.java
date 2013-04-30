package models.dbentities;

import models.management.ManageableModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Contest class database entity.
 *
 * @author Kevin Stobbelaar.
 */
@Entity
@Table(name="contestclasses")
public class ContestClass extends ManageableModel {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="classid")
    public ClassGroup classid;

    @ManyToOne
    @JoinColumn(name="contestid")
    public CompetitionModel contestid;

    /**
     * Returns those field values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        return new String[0];  // Will never be used!
    }

    /**
     * Returns the id (or any primary key field) of the object as a String
     *
     * @return id the primary key for the object this Model contains.
     */
    @Override
    public String getID() {
        return null;  // Will never be used!
    }

}
