package models.question;

import models.data.Language;

/**
 *
 * @author Ruben Taelman
 *
 */
public class RegexAnswer extends Answer{
    private RegexQuestion question;
    private String input;

    public RegexAnswer(RegexQuestion question, String input, Language language) {
        this.question = question;
        this.input = input;
        this.language = language;
    }

    @Override
    public boolean isCorrect() {
        return input != null && input.matches(question.getRegex(language));
    }
    
    public String getInput() {
        return input;
    }
}
