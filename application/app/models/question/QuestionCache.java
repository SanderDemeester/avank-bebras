package models.question;

import java.util.HashMap;
import java.util.Map;

/**
 * A static cache that can contain questions
 * @author Ruben Taelman
 *
 */
public class QuestionCache {
    private static Map<String, Question> questions = new HashMap<String, Question>();

    /**
     * Get a question by id
     * @param id the id of the question
     * @return the question added by that id
     */
    public static Question getQuestion(String id) {
        return questions.get(id);
    }

    /**
     * Add a question
     * @param id id of the question
     * @param question question file
     */
    public static void putQuestion(String id, Question question) {
        questions.put(id, question);
    }

    /**
     * Remove a question from the cache
     * @param id the id of the question
     */
    public static void removeQuestion(String id) {
        questions.remove(id);
    }
}
