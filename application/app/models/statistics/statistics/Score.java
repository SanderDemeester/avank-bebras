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
import models.dbentities.PupilAnswer;

public class Score extends ContinuousStatistic {

    public static final String name = "statistics.statistics.score";

    @Override public Double calculate(UserModel user) {
        double total = 0;
        String sid = "";
        List<PupilAnswer> corrects = Ebean.find(PupilAnswer.class).where()
            .eq("indid", user.id).findList();
        if(corrects.size() == 0) return null;
        for(PupilAnswer b : corrects) if(b.correct && (set == null || b.questionset.equals(set))) total++;
        return total;
    }

    private QuestionSetModel set = null;

    @Override public void setQuestionSet(QuestionSetModel set) {
        this.set = set;
    }

    @Override public String getName() {
        return name;
    }

}

