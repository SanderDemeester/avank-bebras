package models.question;

import java.util.HashMap;
import java.util.Map;

import models.data.Language;

/**
 * A question that will be answered with a textual input with regex check
 * @author Ruben Taelman
 *
 */
public class RegexQuestion extends Question{

    private Map<Language, String> regex;

    /**
     * Creat a new RegexQuestion
     */
    public RegexQuestion() {
        this.type=QuestionType.REGEX;
        this.regex=new HashMap<Language, String>();
    }

    /**
     * Set the regex for a certain language
     * @param language chosen language
     * @param regex regex for the question
     */
    public void setRegex(Language language, String regex) {
        this.regex.put(language, regex);
    }

    /**
     * Get the regex for a certain language
     * @param language chosen language
     * @return regex for the question
     */
    public String getRegex(Language language) {
        return regex.get(language);
    }
}
