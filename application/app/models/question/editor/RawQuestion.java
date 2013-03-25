package models.question.editor;

import java.util.ArrayList;
import java.util.List;

import models.data.Language;
import models.question.QuestionType;
import play.db.ebean.Model;

public class RawQuestion extends Model{
    
    /*public List<String> languages;
    public List<String> files;
    public String addLanguage;
    public static List<Language> languageList = new ArrayList<Language>();
    public String type;
    
    static{
        languageList.add(new Language("en", "English"));
        languageList.add(new Language("nl", "Dutch"));
    }
    
    public RawQuestion() {
        languages = new ArrayList<String>();
    }
    
    public RawQuestion(String type) throws IllegalArgumentException {
        super();
        
        if(QuestionType.valueOf(type)==null)
            throw new IllegalArgumentException("The provided question type does not exist.");
        this.type = type;
        
        // TMP:
        
    }*/
}
