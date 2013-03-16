package controllers;

import play.i18n.Lang;
import play.i18n.Messages;

/**
 * An extension of the default Messages to override default language.
 * @author Ruben Taelman
 *
 */
public class EMessages extends Messages {

    private static Lang customLang = Lang.forCode("en");

    /**
     * Set the language with valid Lang code
     * @param langCode the language code
     */
    public static void setLang(String langCode) {
        customLang = Lang.forCode(langCode);
    }

    /**
     * Translates a message. Uses `java.text.MessageFormat` internally to format the message.
     * @param key the message key
     * @param args the message arguments
     * @return
     */
    public static String get(java.lang.String key, java.lang.Object... args) {
        return Messages.get(customLang, key, args);
    }

}
