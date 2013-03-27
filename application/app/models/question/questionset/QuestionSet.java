package models.question.questionset;

import models.dbentities.QuestionSetModel;

/**
 * Class that contains all logic implementation about question sets.
 *
 * @author Kevin Stobbelaar
 */
public class QuestionSet {

    private QuestionSetModel data;

    /**
     * Default constructor that sets the data model for this question set.
     *
     * @param data question set data model
     */
    public QuestionSet(QuestionSetModel data){
        this.data = data;
    }

}
