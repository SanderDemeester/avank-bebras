package models.question;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

/**
 * A class to transform json answers to QuestionFeedback
 * @author Ruben Taelman
 *
 */
public class QuestionFeedbackGenerator {
    public static QuestionFeedback generateFromJson(JsonNode json) {
        String competition = json.get("competition").getTextValue();
        String questionset = json.get("questionset").getTextValue();
        int timeleft = json.get("timeleft").getIntValue();
        
        JsonNode questions = json.get("questions");
        Iterator<String> it = questions.getFieldNames();
        while(it.hasNext()) {
            String questionID = it.next();
            JsonNode questionNode = questions.get(questionID);
            String input = questionNode.getTextValue();
        }
        return null;
    }
}
