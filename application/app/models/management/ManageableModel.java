package models.management;

import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

/**
 * Interface that an entity must implement in order to make use of
 * default CRUD implementations and the list.scala.html template.
 * Annotate field with models.management.Editable to let the Manager
 * know that this field can be edited from within a form.
 *
 * @auhtor Kevin Stobbelaar, Ruben
 */
@MappedSuperclass
public abstract class ManageableModel extends Model{

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public abstract String[] getFieldValues();

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    public abstract String getID();
}
