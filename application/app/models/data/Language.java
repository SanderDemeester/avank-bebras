package models.data;

/**
 * Represents a Language that can be used in the application.
 * @author Ruben Taelman
 */
public class Language {

    private String code;
    private String translateCode;

    /**
     * Creates a new language with a certain ISO Language Code and a key for that language in the messages
     * @param code    ISO code for the language
     * @param name    key for the language name in the messages file
     */
    public Language(String code, String translateCode) {
        this.code = code;
        this.translateCode = translateCode;
    }

    /**
     * Returns the ISO language code
     * @return ISO language code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the key for this language from the messages file
     * @return key for the language name in the messages file
     */
    public String getTranslateCode() {
        return this.translateCode;
    }

}
