package models.management;

import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

/**
 * Interface that an entity must implement in order to make use of
 * default CRUD implementations and the list.scala.html template.
 * Annotate field with models.management.Editable to let the Manager
 * know that this field can be edited from within a form.
 *
 * @author Kevin Stobbelaar
 * @author Ruben Taelman
 */
@MappedSuperclass
public abstract class ManageableModel extends Model{

    private static final long serialVersionUID = 1L;

    /**
     * Returns those field values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public abstract String[] getFieldValues();

    /**
     * Returns the id (or any primary key field) of the object as a String
     *
     * @return id the primary key for the object this Model contains.
     */
    public abstract String getID();
}
