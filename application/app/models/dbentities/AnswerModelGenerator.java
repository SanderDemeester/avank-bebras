package models.dbentities;

import models.user.User;

public class AnswerModelGenerator {
    public static AnswerModel make(User user) {
        if(user.isAnon())   return new AnonAnswer();
        else                return new PupilAnswer(user.getData());
    }
}
