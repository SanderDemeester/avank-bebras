package models.question;

/**
 *
 * @author Ruben Taelman
 *
 */
public abstract class Answer {
    protected Question question;

    public abstract boolean isCorrect();
}
