package models.data;

/**
 * @author Ruben Taelman
 */
public class Languages {
    public static Language getLanguage(String code) {
        return new Language(code, "THIS IS TEMP");
    }
}
