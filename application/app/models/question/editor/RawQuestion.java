package models.question.editor;

import java.util.ArrayList;
import java.util.List;

import models.data.Language;
import play.db.ebean.Model;

public class RawQuestion extends Model{
    
    public List<String> languages;
    public String addLanguage;
    public static List<Language> languageList = new ArrayList<Language>();
    
    static{
        languageList.add(new Language("en", "English"));
        languageList.add(new Language("nl", "Dutch"));
    }
}
