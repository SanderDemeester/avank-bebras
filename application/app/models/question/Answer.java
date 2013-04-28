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
     * @return if the answer if correct
     */
    public abstract boolean isCorrect();
}
