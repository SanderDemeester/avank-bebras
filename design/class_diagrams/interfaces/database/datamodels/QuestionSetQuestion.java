
package datamodels;

import enums.Difficulty;

/**
 *
 * @author Jens N. Rammant
 */
public class QuestionSetQuestion implements Model{
    private String questionSetID;
    private String questionID;
    private Difficulty difficulty;

    public QuestionSetQuestion(String questionSetID, String questionID, Difficulty difficulty) {
        this.questionSetID = questionSetID;
        this.questionID = questionID;
        this.difficulty = difficulty;
    }

    public String getQuestionSetID() {
        return questionSetID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    
}
