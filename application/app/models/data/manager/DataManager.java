package models.data.manager;

import java.util.List;
import java.lang.RuntimeException;

import javax.persistence.PersistenceException;

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
     * Creates a new object from the Strings supplied.
     * @return The factorized object.
     */
    public abstract T createFromStrings(String... strings)
        throws CreationException;

    /**
     * Adds the given element to the list of data elements.
     * @param element The new element
     */
    @SuppressWarnings("unchecked")
    public void add(DataElement element) {
        try {
            if(element.getClass().equals(getTClass())) Ebean.save((T) element);
            else throw new RuntimeException("You managed with the wrong element.");
        } catch(PersistenceException e) {
            System.out.println(e.getMessage());
        }
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

    private static class ManagerException extends Exception {
        private static final long serialVersionUID = 1L;

        private String emessage;

        /**
         * Creates a new CreationException.
         * @param emessage The EMessage identifier for the message to show the
         * user.
         */
        public ManagerException(String emessage) {
            super();
            this.emessage = emessage;
        }

        /**
         * Creates a new CreationException with a separate debugging exception.
         * @param message The message used for the exception, for debugging,
         * logs, etc...
         * @param emessage The EMessage identifier for the message to show the
         * user.
         */
        public ManagerException(String message, String emessage) {
            super(message);
            this.emessage = emessage;
        }

        /**
         * Returns the EMessage identifier to show the user.
         */
        public String getEMessage() {
            return this.emessage;
        }

    }

    /**
     * Represents any problem with the creations of a <tt>T</tt> object from the
     * list of strings.
     */
    public static class CreationException extends ManagerException {
        private static final long serialVersionUID = 1L;
        public CreationException(String emessage) { super(emessage); }
        public CreationException(String message, String emessage) {
            super(message, emessage);
        }
    }

    /**
     * Represents any problem with saving a <tt>T</tt> object to the database,
     * for instance duplication exceptions.
     * TODO use it
     */
    public static class InsertionException extends ManagerException {
        private static final long serialVersionUID = 1L;
        public InsertionException(String emessage) { super(emessage); }
        public InsertionException(String message, String emessage) {
            super(message, emessage);
        }
    }

    /**
     * Represents any problem with removing a <tt>T</tt> object from the
     * database, which will mostly be unexisting objects someone is driven to
     * remove.
     * TODO use it
     */
    public static class RemovalException extends ManagerException {
        private static final long serialVersionUID = 1L;
        public RemovalException(String emessage) { super(emessage); }
        public RemovalException(String message, String emessage) {
            super(message, emessage);
        }
    }

    /**
     * Represents any problem with listing the values from a manager.
     * TODO use it
     */
    public static class ListException extends ManagerException {
        private static final long serialVersionUID = 1L;
        public ListException(String emessage) { super(emessage); }
        public ListException(String message, String emessage) {
            super(message, emessage);
        }
    }

}
