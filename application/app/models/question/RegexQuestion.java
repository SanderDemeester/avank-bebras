package models.question;

/**
 * A question that will be answered with a textual input with regex check
 * @author Ruben Taelman
 *
 */
public class RegexQuestion extends Question{

    private String regex;

    public RegexQuestion() {
        this.type=QuestionType.REGEX;
    }

    protected String getRegex() {
        return regex;
    }
}
