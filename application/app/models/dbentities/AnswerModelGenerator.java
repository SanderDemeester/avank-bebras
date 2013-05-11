package models.dbentities;

import models.user.User;

/**
 * generator for answers
 * @author Ruben Taelman
 *
 */
public class AnswerModelGenerator {
    /**
     * make an answermodel based on the user type
     * @param user user
     * @return new answer
     */
    public static AnswerModel make(User user) {
        if(user.isAnon())   return new AnonAnswer();
        else                return new PupilAnswer(user.getData());
    }
}
