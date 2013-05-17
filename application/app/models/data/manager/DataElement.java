package models.data.manager;

/**
 * An element to be included in a DataManager.
 * @author Felix Van der Jeugt
 */
public interface DataElement {

    /**
     * Represent this element as a series of strings.
     * @return An array of strings.
     */
    public String[] strings();

    public String id();

}
