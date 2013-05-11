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

    /**
     * Make new regex answer
     * @param question question
     * @param input input
     * @param language language
     */
    public RegexAnswer(RegexQuestion question, String input, Language language) {
        this.question = question;
        this.input = input;
        this.language = language;
    }

    @Override
    public boolean isCorrect() {
        return input != null && input.matches(question.getRegex(language));
    }
    
    /**
     * get input
     * @return input
     */
    public String getInput() {
        return input;
    }
    
    @Override
    public boolean isFilledIn() {
        return input!=null && !input.equals("");
    }

    @Override
    public String getTextValue() {
        if(this.input == null)
            return "";
        return this.input;
    }
}
