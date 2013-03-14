package models.question;

/**
 * 
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceAnswer extends Answer{
	private MultipleChoiceElement element;
	
	public MultipleChoiceAnswer(Question question, MultipleChoiceElement element) {
		
	}
	
	@Override
	public boolean isCorrect() {
		return false;
	}
}
