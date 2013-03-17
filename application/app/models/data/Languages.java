package models.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruben Taelman
 */
public class Languages {
    private static final Map<String, Language> LANGUAGES = new HashMap<String, Language>();
    static{
        LANGUAGES.put("en", new Language("en","tmp"));
        LANGUAGES.put("nl", new Language("nl","tmp"));
    }

    public static Language getLanguage(String code) {
        return LANGUAGES.get(code);
    }
}
