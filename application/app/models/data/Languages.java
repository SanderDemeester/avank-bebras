package models.data;

public class Languages {
    public static Language getLanguage(String code) {
        return new Language(code, "THIS IS TEMP, SHOULD RETURN NULL IF THE CODE IS INVALID");
    }
}
