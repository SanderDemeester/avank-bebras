package models.statistics.statistics;

import java.util.Collection;

import models.dbentities.QuestionModel;
import models.dbentities.QuestionSetModel;

public interface QuestionStatistic {

    public void setQuestion(QuestionModel question);
    public void setQuestionSet(QuestionSetModel set);
    public Collection<QuestionModel> getQuestions();

}
