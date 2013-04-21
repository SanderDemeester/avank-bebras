package models.question;

import models.data.Language;

/**
 *
 * @author Ruben Taelman
 *
 */
public abstract class Answer {
    protected Language language;
    
    public abstract boolean isCorrect();
}
