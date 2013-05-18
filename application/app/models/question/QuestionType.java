package models.question;

/**
 *
 * @author Ruben Taelman
 *
 */
public enum QuestionType {
    /**
     * multiple choice question
     */
    MULTIPLE_CHOICE("multiple-choice-question"),
    /**
     * regex question
     */
    REGEX("regex-question");

    private String xmlElement;

    private QuestionType(String xmlElement) {
        this.xmlElement=xmlElement;
    }

    /**
     * get xml tag name
     * @return tag name
     */
    public String getXmlElement() {
        return this.xmlElement;
    }
}
