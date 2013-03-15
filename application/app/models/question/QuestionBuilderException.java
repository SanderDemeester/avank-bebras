package models.question;

/**
 * An exception that can be thrown while generating a Question from an XML file
 * @author Ruben Taelman
 *
 */
public class QuestionBuilderException extends Exception{
    
    public QuestionBuilderException(String message) {
        super(message);
    }
}
