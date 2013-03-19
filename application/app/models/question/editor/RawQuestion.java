package models.question.editor;

import java.util.ArrayList;
import java.util.List;

import play.db.ebean.Model;

public class RawQuestion extends Model{
    public List<String> languages;
    public static List<String> languageList = new ArrayList<String>();
    static{
        languageList.add("en");
        languageList.add("nl");
    }
}
