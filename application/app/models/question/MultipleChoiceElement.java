package models.question;

/**
 * 
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceElement {
	private String content;
	
	public MultipleChoiceElement(String content) {
		
	}

	public String getContent() {
		return content;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultipleChoiceElement other = (MultipleChoiceElement) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}
	
	
}
