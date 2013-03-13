
package models.data;

/**
 * This class represents one of the Frequently Asked Questions.
 *
 * At the moment, you can only create new FAQs by provided two string. This will
 * probably be extended to multiple constructors, but it's just a stub now.
 *
 * @author Felix Van der Jeugt
 */
public class FAQ {

    private String question;
    private String answer;

    /**
     * Creates a FAQ by providing the question and answer as plain String.
     * @param question The FAQ.
     * @param answer The answer to above FAQ.
     */
    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
