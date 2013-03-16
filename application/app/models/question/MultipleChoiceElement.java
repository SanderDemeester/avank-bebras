package models.question;

/**
 * An answerable element in a MultipleChoice Question
 * @author Ruben Taelman
 *
 */
public class MultipleChoiceElement {

    private String content;

    /**
     * Makes a new MultipleChoiceElement based on an input text that can be selected
     * @param content  the text that will appear next to the radio-button
     */
    public MultipleChoiceElement(String content) {
        this.content=content;
    }

    /**
     * Get the input text for this element
     * @return the text that will appear next to the radio-button
     */
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
