package models.question;

import java.util.Map;

/**
 * 
 * @author Ruben Taelman
 *
 */
public class QuestionFeedback {
	private Map<Question, FeedbackElement> feedbackElements;
	private int score;
	
	public QuestionFeedback(Map<Question, Answer> answers) {
		
	}

	public Map<Question, FeedbackElement> getFeedbackElements() {
		return feedbackElements;
	}

	public int getScore() {
		return score;
	}
}
