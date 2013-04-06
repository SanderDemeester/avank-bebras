package models.management;

/**
 * Interface that an entity must implement in order to make use of
 * default CRUD implementations and the list.scala.html template.
 *
 * @auhtor Kevin Stobbelaar
 */
public interface Manageable {

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public String[] getFieldValues();

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    public String getID();


    /**
     * Returns the name of the object.
     *
     * @return name
     */
    public String getName();
}
