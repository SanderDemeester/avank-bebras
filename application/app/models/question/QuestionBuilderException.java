package models.question;

/**
 * An exception that can be thrown while generating a Question from an XML file
 * @author kroeser
 *
 */
public class QuestionBuilderException extends Exception{
    
    public QuestionBuilderException(String message) {
        super(message);
    }
}
