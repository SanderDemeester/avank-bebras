package models.statistics.statistics;

import java.lang.String;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.dbentities.UserModel;
import models.dbentities.QuestionModel;
import models.dbentities.PupilAnswer;

public class Answer extends DiscreteStatistic {

    public static final String name = "statistics.statistics.answer";

    @Override public String calculate(UserModel user) {
        if(question == null) return null;
        List<PupilAnswer> answers = Ebean.find(PupilAnswer.class).where()
            .eq("indid", user.id)
            .eq("qid", question)
            .findList();
        if(answers == null || answers.size() != 1) return null;
        return answers.get(0).answer;
    }

    @Override public String getName() {
        return name;
    }

    private Integer question = null;

    @Override public void setQuestion(Integer questionid) {
        this.question = questionid;
    }

    @Override public String extraID() {
        return "qid";
    }

}


