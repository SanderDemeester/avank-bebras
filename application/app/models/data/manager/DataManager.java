package models.data.manager;

import java.util.List;
import java.lang.RuntimeException;

import com.avaje.ebean.Ebean;

/**
 * A simple management class for editting simple data objects.
 * @see controllers.data.DataController
 * @author Felix Van der Jeugt
 */
public abstract class DataManager<T extends DataElement> {

    private T removed;

    /**
     * List the column titles for this Manager
     * @return The column titles.
     */
    public abstract String[] columns();

    /**
     * Returns the base url for this manager.
     */
    public abstract String url();

    /**
     * Returns the title for this Editor page.
     */
    public abstract String title();

    /**
     * Returns the class this Manager manages.
     */
    public abstract Class<T> getTClass();

    /**
     * Adds the given element to the list of data elements.
     * @param element The new element
     */
    @SuppressWarnings("unchecked") public void add(DataElement element) {
        if(element.getClass().equals(getTClass())) Ebean.save((T) element);
        else throw new RuntimeException("You managed with the wrong element.");
    }

    /**
     * Removes the element with the given id from the list of data element.
     * @param id The identifier
     */
    public void remove(String id) {
        removed = Ebean.find(getTClass(), id);
        Ebean.delete(getTClass(), id);
    }

    /**
     * Returns the element that was last removed as an array of strings.
     */
    public String[] lastRemoved() {
        return removed.strings();
    }

    /**
     * List the stringy representations of the data elements.
     * @return The table of the elements.
     */
    public List<T> list() {
        return Ebean.find(getTClass()).findList();
    }

}
