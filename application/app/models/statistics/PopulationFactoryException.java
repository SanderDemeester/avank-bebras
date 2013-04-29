package models.statistics;

/**
 * Exception thrown to indicate a factory could not create a Population from the
 * given identifier.
 * @author Felix Van der Jeugt
 */
public class PopulationFactoryException extends Exception {
    private static final long serialVersionUID = 1L;

    public PopulationFactoryException(Exception e) {
        super(e);
    }

    public PopulationFactoryException(String message) {
        super(message);
    }

}
