package models.data;

import models.data.LanguageCreationException;

/**
 * Indicates the creation of a models.data.Language object with a language that
 * is not available in this application as parameter.
 * @author Felix Van der Jeugt
 */
public class UnavailableLanguageException extends LanguageCreationException {
    private static final long serialVersionUID = 1L;

    /**
     * New exception
     * @param code language code
     */
    public UnavailableLanguageException(String code) {
        super("Language code exists but is not supported: " + code);
    }

}
