package models.competition;

/**
 * An exception that can occur when a competition has not been started yet when adding properties to
 * its state.
 * @author Ruben Taelman
 *
 */
public class CompetitionNotStartedException extends Exception{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CompetitionNotStartedException(String msg) {
        super(msg);
    }
}
