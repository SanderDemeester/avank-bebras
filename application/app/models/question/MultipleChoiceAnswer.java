package models.question;

import models.data.Language;

/**
 * A multiple choice question answer
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceAnswer extends Answer{
    private MultipleChoiceQuestion question;
    private MultipleChoiceElement element;

    /**
     * Make a new MultipleChoiceAnswer
     * @param question question for the answer
     * @param element selected element
     * @param language language in which the answer was given
     */
    public MultipleChoiceAnswer(MultipleChoiceQuestion question, MultipleChoiceElement element, Language language) {
        this.question = question;
        this.element = element;
        this.language = language;
    }

    @Override
    public boolean isCorrect() {
        return element != null && question.getCorrectElement(language).equals(element);
    }
    
    /**
     * Get the selected element
     * @return the selected element
     */
    public MultipleChoiceElement getElement() {
        return element;
    }
}
