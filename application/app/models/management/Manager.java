package models.management;

import com.avaje.ebean.Page;

/**
 * Manager interface for every model that needs visual CRUD operations.
 * An entity must implement this interface in order to use the view list.scala.html
 *
 * @author Kevin Stobbelaar
 *
 */
public interface Manager {

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public String[] getFieldValues();

    /**
     * Returns the names of the fields that have to be represented in a table.
     * These will be the table headers.
     *
     * @return array with the names of fields to be represented in the table
     */
    public String[] getFieldNames();

    /**
     * Returns the id of the this object.
     *
     * @return id
     */
    public String getID();

}
