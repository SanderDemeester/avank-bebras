package models.question;

import java.util.ArrayList;
import java.util.List;

/**
 * A question that will be answered with a multiple choice input
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceQuestion extends Question{
	private List<MultipleChoiceElement> elements;
	private MultipleChoiceElement correctElement;
	
	protected MultipleChoiceQuestion() {
		super();
		this.type=QuestionType.MULTIPLE_CHOICE;
		this.elements = new ArrayList<MultipleChoiceElement>();
	}

	public List<MultipleChoiceElement> getElements() {
		return elements;
	}

	public MultipleChoiceElement getCorrectElement() {
		return correctElement;
	}
}
