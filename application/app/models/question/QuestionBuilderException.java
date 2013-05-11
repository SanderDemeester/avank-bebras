package models.question;

/**
 * An exception that can be thrown while generating a Question from an XML file
 * @author Ruben Taelman
 *
 */
public class QuestionBuilderException extends Exception{
    private static final long serialVersionUID = 1L;

    /**
     * new exception
     * @param message message
     */
    public QuestionBuilderException(String message) {
        super(message);
    }
}
