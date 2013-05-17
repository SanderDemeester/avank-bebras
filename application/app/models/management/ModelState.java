package models.management;

/**
 * The different states a Manager can have. The background logic of the Manager might be different
 * for different ModelState's.
 * @author Ruben Taelman
 *
 */
public enum ModelState {
    /**
     * create
     */
    CREATE,
    /**
     * read
     */
    READ,
    /**
     * update
     */
    UPDATE,
    /**
     * delete
     */
    DELETE
}
