package models.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class can hold the feedback and answers for a complete questionset
 * @author Ruben Taelman
 *
 */
public class QuestionFeedback {
    private Map<Question, Boolean> feedbackElements;
    private Map<Question, Answer> answers;
    private int score;

    /**
     * Generate feedback from a given map of answers
     * @param answers the questions mapped on answers
     * @param competitionID the id of the competition
     * @param questionSetID the id of the questionset
     * @param timeLeft the time left in seconds when the answers were submitted
     */
    public QuestionFeedback(Map<Question, Answer> answers, String competitionID, String questionSetID, int timeLeft) {
        makeFeedbackElements(answers);
        this.answers = answers;
        // TODO: calculate score
        this.score = 9001;
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
}
