package models.question;

import models.data.Language;

/**
 * Abstract class to hold answers
 * @author Ruben Taelman
 *
 */
public abstract class Answer {
    protected Language language;

    /**
     * Returns if the answer was answered correctly
     * @return if the answer is correct
     */
    public abstract boolean isCorrect();

    /**
     * Returns if the answer was filled in
     * @return if the answer is filled in
     */
    public abstract boolean isFilledIn();

    /**
     * The textual value of the inputted answer
     * @return the answer in text format
     */
    public abstract String getTextValue();
}
