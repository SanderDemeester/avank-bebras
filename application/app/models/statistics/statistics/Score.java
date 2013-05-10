package models.statistics.statistics;

import java.lang.Double;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.avaje.ebean.Ebean;

import models.statistics.populations.Population;
import models.dbentities.UserModel;
import models.dbentities.QuestionModel;
import models.dbentities.QuestionSetModel;

public class Score extends ContinuousStatistic {

    public static final String name = "statistics.statistics.score";

    @Override public Double calculate(UserModel user) {
        double total = 0;
        List<Boolean> corrects = new ArrayList<Boolean>();
        if(question != null) corrects = Ebean.createQuery(
            Boolean.class,
            "select correct from pupilanswers where qid = " + question.id
        ).findList();
        else if(set != null) corrects = Ebean.createQuery(
            Boolean.class,
            "select correct from pupilanswers where questionsetid = " + set.id
        ).findList();
        for(boolean b : corrects) if(b) total++;
        return total;
    }

    private QuestionModel question = null;
    private QuestionSetModel set = null;

    @Override public void setQuestion(QuestionModel question) {
        this.question = question;
        this.set = null;
    }

    @Override public void setQuestionSet(QuestionSetModel set) {
        this.set = set;
        this.question = null;
    }

    @Override public String getName() {
        return name;
    }

}

