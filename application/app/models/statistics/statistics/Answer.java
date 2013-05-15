package models.statistics.statistics;

import java.lang.String;
import java.lang.Integer;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.statistics.populations.Population;
import models.dbentities.UserModel;
import models.dbentities.QuestionModel;
import models.dbentities.PupilAnswer;

public class Answer extends DiscreteStatistic {

    public static final String name = "statistics.statistics.answer";

    @Override public String calculate(UserModel user) {
        if(question == null) return null;
        List<PupilAnswer> answers = Ebean.find(PupilAnswer.class).where()
            .eq("indid", user.id)
            .eq("qid", question.id)
            .findList();
        if(answers == null) return null;
        return answers.get(0).answer;
    }

    @Override public String getName() {
        return name;
    }

    private QuestionModel question = null;

    @Override public void setQuestion(QuestionModel question) {
        this.question = question;
    }

}


