package models.data.manager;

/**
 * An element to be included in a DataManager.
 * @see controllers.data.DataController
 * @author Felix Van der Jeugt
 */
public interface DataElement {

    /**
     * Represent this element as a series of strings.
     * @return An array of strings.
     */
    public String[] strings();

    /**
     * Get the id of the data element
     * @return id of the data element
     */
    public String id();

}
