package models.statistics.statistics;

import java.lang.Double;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.dbentities.UserModel;

public class Score extends ContinuousStatistic {

    public static final String name = "statistics.statistics.score";

    @Override public Double calculate(UserModel user) {
        List<models.dbentities.Score> scores =
            Ebean.find(models.dbentities.Score.class)
                .where().eq("uID", user.id).findList();
        if(scores == null || scores.size() == 0) return null;
        if(set == null) {
            double total = 0;
            for(models.dbentities.Score score : scores) total += score.score;
            return total;
        } else {
            double s = 0;
            for(models.dbentities.Score score : scores) {
                if(score.questionset.id == set) s = score.score;
            }
            return s;
        }
    }

    private Integer set = null;

    @Override public void setQuestionSet(Integer set) {
        this.set = set;
    }

    @Override public String getName() {
        return name;
    }

    @Override public String extraID() {
        return "qsid";
    }

}

