package models.question.questionset;

import models.competition.Competition;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Language;
import models.dbentities.QuestionSetModel;
import models.question.Question;

import java.util.List;
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

    /**
     * Returns the languages for this question set.
     *
     * @return supported languages
     */
    public List<Language> getLanguages(){
        return data.languages;
    }

    /**
     * Returns true if the question set is active.
     *
     * @return true if active, else false
     */
    public boolean isActive(){
        return data.active;
    }

    /**
     * Setter for the active field.
     *
     * @param active the new active state
     */
    public void setActive(boolean active){
        data.active = active;
    }

    /**
     * Getter for competition field.
     *
     * @return the competition for this question set
     */
    public Competition getCompetition(){
        return data.competition;
    }

    /**
     * Setter for the competition field.
     *
     * @param competition competition for this question set
     */
    public void setCompetition(Competition competition){
        data.competition = competition;
    }

    /**
     * Getter for the grade field.
     *
     * @return the grade of this question set
     */
    public Grade getGrade(){
        return data.grade;
    }

    /**
     * Setter for the grade field.
     *
     * @param grade new grade for this question set
     */
    public void setGrade(Grade grade){
        data.grade = grade;
    }

    /**
     * Gets the difficulty of a specific question.
     *
     * @param question the question wherefore the difficulty is asked
     * @return difficulty of the given question
     */
    public Difficulty getDifficulty(Question question){
        return data.difficulties.get(question);
    }

    /**
     * Adds a new difficulty for the given question.
     *
     * @param question the question wherefore the difficulty is set
     * @param difficulty the new difficulty for the given question
     */
    public void setDifficulty(Question question, Difficulty difficulty){
        data.difficulties.put(question, difficulty);
    }

    // TODO what does this method has to do?
    public boolean canActivate(){
        return true;
    }

}