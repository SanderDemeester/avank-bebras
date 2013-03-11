
package datamodels;

/**
 *
 * @author Jens N. Rammant
 */
public class Answer implements Model{
    private String questionID;
    private String competitionID;
    private String userID;
    private String answer;

    public Answer(String questionID, String competitionID, String userID, String answer) {
        this.questionID = questionID;
        this.competitionID = competitionID;
        this.userID = userID;
        this.answer = answer;
    }

    public String getQuestionID() {
        return questionID;
    }

    public String getCompetitionID() {
        return competitionID;
    }

    public String getUserID() {
        return userID;
    }

    public String getAnswer() {
        return answer;
    }

    
    
}
