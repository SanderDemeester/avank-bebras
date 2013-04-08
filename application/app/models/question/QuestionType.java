package models.question;

/**
 *
 * @author Ruben Taelman
 *
 */
public enum QuestionType {
    MULTIPLE_CHOICE("multiple-choice-question"), REGEX("regex-question");
    
    private String xmlElement;
    
    private QuestionType(String xmlElement) {
        this.xmlElement=xmlElement;
    }
    
    public String getXmlElement() {
        return this.xmlElement;
    }
}
