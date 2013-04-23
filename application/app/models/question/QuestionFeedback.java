package models.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Ruben Taelman
 *
 */
public class QuestionFeedback {
    private Map<Question, Boolean> feedbackElements;
    private int score;

    public QuestionFeedback(Map<Question, Answer> answers, String competitionID, String questionSetID, int timeLeft) {
        makeFeedbackElements(answers);
        // TODO: calculate score
    }
    
    private void makeFeedbackElements(Map<Question, Answer> answers) {
        feedbackElements = new HashMap<Question, Boolean>();
        for(Entry<Question, Answer> entry : answers.entrySet()) {
            Question question = entry.getKey();
            Answer answer = entry.getValue();
            feedbackElements.put(question, answer.isCorrect());
        }
    }

    public Map<Question, Boolean> getFeedbackElements() {
        return feedbackElements;
    }

    public int getScore() {
        return score;
    }
}
