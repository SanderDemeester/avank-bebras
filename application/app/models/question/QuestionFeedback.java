package models.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import models.data.Difficulty;
import models.dbentities.QuestionSetModel;

import com.avaje.ebean.Ebean;

/**
 * This class can hold the feedback and answers for a complete questionset
 * @author Ruben Taelman
 *
 */
public class QuestionFeedback {
    private Map<Question, Boolean> feedbackElements;
    private Map<Question, Answer> answers;
    private int score;

    private String competitionID;
    private int questionSetID;
    private String userid;
    private String languageCode;

    /**
     * Generate feedback from a given map of answers
     * @param answers the questions mapped on answers
     * @param competitionID the id of the competition
     * @param questionSetID the id of the questionset
     * @param userid the token of the person who takes the competition
     * @param timeLeft the time left in seconds when the answers were submitted
     * @param languageCode the language code the answers were filled in
     */
    public QuestionFeedback(Map<Question, Answer> answers, String competitionID, String questionSetID, int timeLeft, String userid, String languageCode) {
        makeFeedbackElements(answers);
        this.answers = answers;

        this.competitionID = competitionID;
        this.questionSetID = Integer.parseInt(questionSetID);
        this.userid = userid;
        this.languageCode = languageCode;

        calculateScore();
    }

    public String getCompetitionID() {
        return this.competitionID;
    }

    public int getQuestionSetID() {
        return this.questionSetID;
    }

    private void calculateScore() {
        QuestionSetModel qsModel = Ebean.find(QuestionSetModel.class).where().idEq(this.questionSetID).findUnique();
        QuestionSet questionSet = new QuestionSet(qsModel);
        for(Entry<Question, Answer> e : answers.entrySet()) {
            Difficulty diff = questionSet.getDifficulty(e.getKey());
            if(e.getValue().isFilledIn()) {
                if(e.getValue().isCorrect()) score += diff.cpoints;
                else                         score += diff.wpoints;
            } else {
                score += diff.npoints;
            }
        }
    }

    private void makeFeedbackElements(Map<Question, Answer> answers) {
        feedbackElements = new HashMap<Question, Boolean>();
        for(Entry<Question, Answer> entry : answers.entrySet()) {
            Question question = entry.getKey();
            Answer answer = entry.getValue();
            feedbackElements.put(question, answer.isCorrect());
        }
    }

    /**
     * Get the feedback items
     * @return the questions mapped on booleans
     */
    public Map<Question, Boolean> getFeedbackElements() {
        return feedbackElements;
    }

    /**
     * Get the answers
     * @return the questions mapped on answers
     */
    public Map<Question, Answer> getAnswers() {
        return answers;
    }

    /**
     * The total score from this set of questions and their answers
     * @return the total score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the token for the CompetitionUserState for this user
     * @return the token for this user
     */
    public String getToken() {
        return this.userid;
    }

    /**
     * Returns the language code the answers were solved in
     * @return language code
     */
    public String getLanguageCode() {
        return this.languageCode;
    }
}
