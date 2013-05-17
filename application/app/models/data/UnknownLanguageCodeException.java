package models.data;

import models.data.LanguageCreationException;

/**
 * Indicates the creation of a models.data.Language object with an unexisting
 * language code as parameter.
 * @author Felix Van der Jeugt
 */
public class UnknownLanguageCodeException extends LanguageCreationException {
    private static final long serialVersionUID = 1L;

    /**
     * New exception
     * @param code language code
     */
    public UnknownLanguageCodeException(String code) {
        super("Language code does not exists: " + code);
    }

}
