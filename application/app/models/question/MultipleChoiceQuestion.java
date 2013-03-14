package models.question;

import java.util.List;

/**
 * 
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceQuestion extends Question{
	private List<MultipleChoiceElement> elements;
	private MultipleChoiceElement correctElement;
	
	protected MultipleChoiceQuestion() {
		super();
	}

	public List<MultipleChoiceElement> getElements() {
		return elements;
	}

	public MultipleChoiceElement getCorrectElement() {
		return correctElement;
	}
}
