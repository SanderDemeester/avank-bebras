package models.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.data.Language;

/**
 * A question that will be answered with a multiple choice input
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceQuestion extends Question{

    protected Map<Language, List<MultipleChoiceElement>> elements;
    protected Map<Language, MultipleChoiceElement> correctElement;

    /**
     * Create a new MultipleChoiceQuestion
     */
    protected MultipleChoiceQuestion() {
        super();
        this.type=QuestionType.MULTIPLE_CHOICE;
        this.elements = new HashMap<Language, List<MultipleChoiceElement>>();
        this.correctElement = new HashMap<Language, MultipleChoiceElement>();
    }

    /**
     * Adds a language to this Question
     */
    @Override
    public void addLanguage(Language language) {
        super.addLanguage(language);
        this.elements.put(language, new ArrayList<MultipleChoiceElement>());
    }

    /**
     * Get MultipleChoiceQuestion for a certain Language
     * @param language chosen Language
     * @return a list of MultipleChoiceElements
     */
    public List<MultipleChoiceElement> getElements(Language language) {
        return elements.get(language);
    }

    /**
     * Get the correct MultipleChoiceElement for a cerain Language
     * @param language chosen Language
     * @return the correct MultipleChoiceElement
     */
    public MultipleChoiceElement getCorrectElement(Language language) {
        return correctElement.get(language);
    }

    /**
     * Add a MultipleChoiceElement for a certain Language
     * @param language chosen Language
     * @param element a possible MultipleChoiceElement
     */
    public void addElement(Language language, MultipleChoiceElement element) {
        elements.get(language).add(element);
    }

    /**
     * Set the correct MultipleChoiceElement for a certain Language
     * @param language chosen Language
     * @param element the correct MultipleChoiceElement
     */
    public void setCorrectElement(Language language, MultipleChoiceElement element) {
        correctElement.put(language, element);
    }

    @Override
    public Answer getAnswerByInput(String input, Language language) throws AnswerGeneratorException {
        MultipleChoiceElement chosenElement = null;
        for (MultipleChoiceElement e : elements.get(language)) {
            if(e.getContent().equals(input)) chosenElement = e;
        }

        if(chosenElement == null && input != null)
            throw new AnswerGeneratorException("Invalid multiple choice element.");
        return new MultipleChoiceAnswer(this, chosenElement, language);
    }
}
