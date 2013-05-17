package models.question;

/**
 * Exception that can be thrown when something goes wrong while generating the feedback from answers
 * @author Ruben Taelman
 *
 */
public class AnswerGeneratorException extends Exception{
    private static final long serialVersionUID = 1L;

    /**
     * new exception
     * @param message message
     */
    public AnswerGeneratorException(String message) {
        super(message);
    }
}
