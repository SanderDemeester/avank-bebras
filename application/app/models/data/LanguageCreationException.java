package models.data;

import java.lang.Exception;

/**
 * Class to wrap all Language Creation Exceptions together.
 * @author Felix Van der Jeugt
 */
public class LanguageCreationException extends Exception {

    public LanguageCreationException(String message) {
        super(message);
    }

}
