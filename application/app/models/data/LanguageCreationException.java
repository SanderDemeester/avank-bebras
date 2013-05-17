package models.data;

import java.lang.Exception;

/**
 * Class to wrap all Language Creation Exceptions together.
 * @author Felix Van der Jeugt
 */
public class LanguageCreationException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * New exception
     * @param message message
     */
    public LanguageCreationException(String message) {
        super(message);
    }

}
