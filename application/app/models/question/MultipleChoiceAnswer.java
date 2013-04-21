package models.question;

import models.data.Language;

/**
 *
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceAnswer extends Answer{
    private MultipleChoiceQuestion question;
    private MultipleChoiceElement element;

    public MultipleChoiceAnswer(MultipleChoiceQuestion question, MultipleChoiceElement element, Language language) {
        this.question = question;
        this.element = element;
        this.language = language;
    }

    @Override
    public boolean isCorrect() {
        return element != null && question.getCorrectElement(language).equals(element);
    }
}
