package models.question;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.EMessages;
import models.data.Language;
import models.data.UnavailableLanguageException;
import models.data.UnknownLanguageCodeException;
import models.user.AuthenticationManager;
import models.user.User;

import org.codehaus.jackson.JsonNode;

import play.Logger;

/**
 * A class to transform json answers to QuestionFeedback
 * @author Ruben Taelman
 *
 */
public class QuestionFeedbackGenerator {
    
    /**
     * Generate QuestionFeedback from the answers provided by the user that is encoded in json
     * @param json the answers from the user in json format
     * @return a new QuestionFeedback for these answers
     * @throws AnswerGeneratorException if the answers were somehow invalid (but they can be incorrect)
     */
    public static QuestionFeedback generateFromJson(JsonNode json, Language language) throws AnswerGeneratorException {
        Map<Question, Answer> inputMap = new HashMap<Question, Answer>();
        
        // Info values
        String competition = json.get("competition").getTextValue();
        String questionset = json.get("questionset").getTextValue();
        String userid = json.get("userid").getTextValue();
        int timeleft = json.get("timeleft").getIntValue();
        
        // Loop over the questions
        JsonNode questions = json.get("questions");
        Iterator<String> it = questions.getFieldNames();
        while(it.hasNext()) {
            String questionID = it.next();
            JsonNode questionNode = questions.get(questionID);
            String input = questionNode.getTextValue();
            
            // Try to fetch the questions by their given id
            Question question;
            try {
                question = Question.fetch(questionID);
            } catch (QuestionBuilderException e) {
                throw new AnswerGeneratorException(e.getMessage());
            }
            // Save the answer
            inputMap.put(question, question.getAnswerByInput(input, language));
        }
        return new QuestionFeedback(inputMap, competition, questionset, timeleft, userid);
    }
}
