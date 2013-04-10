package models.management;

/**
 * The different states a Manager can have. The background logic of the Manager might be different
 * for different ModelState's.
 * @author Ruben Taelman
 *
 */
public enum ModelState {
    CREATE,
    READ,
    UPDATE,
    DELETE
}
