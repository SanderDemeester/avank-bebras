package models.data.manager;

import java.util.List;
import java.lang.RuntimeException;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

/**
 * A simple management class for editting simple data objects.
 * @see controllers.data.DataController
 * @author Felix Van der Jeugt
 * @param <T> A type of data element
 */
public abstract class DataManager<T extends DataElement> {

    /**
     * This array of strings will be shown in the input fields. This is useful
     * for values that were just removed, then they can be editted.
     */
    protected String[] removed;

    /**
     * List the column titles for this Manager
     * @return The column titles.
     */
    public abstract String[] columns();

    /**
     * Returns the base url for this manager.
     * @return url of the manager
     */
    public abstract String url();

    /**
     * Returns the title for this Editor page.
     * @return title of the page
     */
    public abstract String title();

    /**
     * Returns the class this Manager manages.
     * @return class of T
     */
    public abstract Class<T> getTClass();

    /**
     * Creates a new object from the Strings supplied.
     * @param strings strings
     * @return The factorized object.
     * @throws CreationException 
     */
    public abstract T createFromStrings(String... strings)
        throws CreationException;

    /**
     * Adds the given element to the list of data elements.
     * @param element The new element
     * @throws InsertionException 
     */
    @SuppressWarnings("unchecked")
    public void add(DataElement element) throws InsertionException {
        try {
            if(element.getClass().equals(getTClass())) Ebean.save((T) element);
            else throw new RuntimeException("You managed with the wrong element.");
        } catch(PersistenceException e) {
            if(e.getMessage().contains("duplicate")) {
                throw new InsertionException("Duplicate element.",
                        "manager.error.duplicate");
            } else throw e;
        }
        removed = null;
    }

    /**
     * Removes the element with the given id from the list of data element.
     * @param id The identifier
     * @throws RemovalException 
     */
    public void remove(String id) throws RemovalException {
        T remove = Ebean.find(getTClass(), id);
        Ebean.delete(getTClass(), id);
        if(remove == null) {
            removed = null;
            throw new RemovalException(
                "Nothing there to remove.",
                "manager.error.notthere"
            );
        }
        removed = remove.strings();
    }

    /**
     * Returns the element that was last removed.
     * @return the last removed
     */
    public String[] lastRemoved() {
        return removed;
    }

    /**
     * List the stringy representations of the data elements.
     * @return The table of the elements.
     */
    public List<T> list() {
        return Ebean.find(getTClass()).findList();
    }

    /**
     * A ManagerException
     */
    public static class ManagerException extends Exception {
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
         * @return the message
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
        /**
         * @param emessage translated message
         */
        public CreationException(String emessage) { super(emessage); }
        /**
         * @param message message
         * @param emessage translated message
         */
        public CreationException(String message, String emessage) {
            super(message, emessage);
        }
    }

    /**
     * Represents any problem with saving a <tt>T</tt> object to the database,
     * for instance duplication exceptions.
     */
    public static class InsertionException extends ManagerException {
        private static final long serialVersionUID = 1L;
        /**
         * @param emessage translated message
         */
        public InsertionException(String emessage) { super(emessage); }
        /**
         * @param message message
         * @param emessage translated message
         */
        public InsertionException(String message, String emessage) {
            super(message, emessage);
        }
    }

    /**
     * Represents any problem with removing a <tt>T</tt> object from the
     * database, which will mostly be unexisting objects someone is driven to
     * remove.
     */
    public static class RemovalException extends ManagerException {
        private static final long serialVersionUID = 1L;
        /**
         * @param emessage translated message
         */
        public RemovalException(String emessage) { super(emessage); }
        /**
         * @param message message
         * @param emessage translated message
         */
        public RemovalException(String message, String emessage) {
            super(message, emessage);
        }
    }

}
