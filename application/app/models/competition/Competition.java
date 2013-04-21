package models.competition;

import com.avaje.ebean.Ebean;
import models.data.Grade;
import models.data.Language;
import models.dbentities.ClassGroup;
import models.dbentities.CompetitionModel;
import models.dbentities.QuestionSetModel;
import models.dbentities.QuestionSetQuestion;
import models.question.Question;
import models.question.QuestionFeedback;
import models.question.QuestionSet;
import models.user.User;

import java.util.*;

/**
 * Class that contains all logic implementation about competitions.
 *
 * @author Kevin Stobbelaar.
 *
 */
public class Competition {

    private CompetitionModel data;
    private List<QuestionSet> questionSets;

    /**
     * Default constructor
     * @param data database entity
     */
    public Competition(CompetitionModel data){
        this.data = data;

        // setting the question sets for this contest
        List<QuestionSetModel> questionSetModels = Ebean.find(QuestionSetModel.class).where().ieq("contid", data.id).findList();
        for (QuestionSetModel questionSetModel : questionSetModels){
            questionSets.add(new QuestionSet(questionSetModel));
        }
    }

    /**
     * Returns the database model for this competition.
     * @return underlying database model
     */
    public CompetitionModel getCompetitionModel(){
        return data;
    }

    /**
     * Sets the type for this competition.
     * @param type new competition type
     */
    public void setType(CompetitionType type){
        data.type = type;
    }

    /**
     * Gets the type for this competition.
     * @return competition's type
     */
    public CompetitionType getType(){
        return data.type;
    }

    /**
     * Gets the duration for this competition.
     * @return
     */
    public int getDuration(){
        return data.duration;
    }

    /**
     * Sets the duration for this competition.
     */
    public void setDuration(int duration){
        data.duration = duration;
    }

    /**
     * Returns the available languages for this competition.
     * @return available languages
     */
    public List<Language> getAvailableLanguages(){
        List<Language> languages = new ArrayList<Language>();
        for (QuestionSet questionSet : questionSets){
            for (Language l : questionSet.getLanguages())
            if (!languages.contains(l)){
                languages.add(l);
            }
        }
        return languages;
    }

    /**
     * Returns the available grades for this competition.
     * @return
     */
    public List<Grade> getAvailableGrades(){
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether this competition can be opened or not.
     * @return true if this competition can be opened.
     */
    public boolean canOpenCompetition(){
        throw new UnsupportedOperationException();
    }

    /**
     * Opens this competition.
     * @param plannedStartDate planned start date
     */
    public void openCompetition(Date plannedStartDate){
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether this competition can be started or not.
     * @return true if this competition can be started.
     */
    public boolean canStartCompetition(){
        throw new UnsupportedOperationException();
    }

    /**
     * Starts this competition.
     * @param classGroups class groups who can take this competition
     */
    public void startCompetition(List<ClassGroup> classGroups){
        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the pupil can join the competition.
     * @param pupil pupil
     * @return true if the pupil can join the competition
     */
    public boolean canJoinPupil(User pupil){
        throw new UnsupportedOperationException();
    }

    /**
     * Joins the pupil.
     * @param pupil pupil
     * @return competition user state
     */
    public CompetitionUserState joinPupil(User pupil){
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the question set for this pupil.
     * @param pupil pupil
     * @return question set
     */
    public QuestionSet getQuestionSet(User pupil){
        throw new UnsupportedOperationException();
    }

    /**
     * Finishes the competition for the pupil.
     * @param pupil pupil
     * @return question feedback
     */
    public QuestionFeedback finishForPupil(User pupil){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the competition state for this pupil.
     * @param pupil pupil
     * @return competition state
     */
    public CompetitionState getCompetitionState(User pupil){
        throw new UnsupportedOperationException();
    }

}
