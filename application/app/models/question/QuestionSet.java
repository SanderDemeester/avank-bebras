package models.question;

import com.avaje.ebean.Ebean;
import models.competition.Competition;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Language;
import models.dbentities.QuestionSetModel;
import models.dbentities.QuestionSetQuestion;
import models.question.Question;
import models.question.QuestionBuilderException;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that contains all logic implementation about question sets.
 *
 * @author Kevin Stobbelaar
 */
public class QuestionSet {

    private QuestionSetModel data;
    private List<Question> questions;

    /**
     * Default constructor that sets the data model for this question set.
     *
     * @param data question set data model
     */
    public QuestionSet(QuestionSetModel data) {
        this.data = data;
        this.questions = new ArrayList<Question>();
        List<QuestionSetQuestion> questionSetQuestions = Ebean.find(QuestionSetQuestion.class).where().eq("qsid", data.id).findList();
        for (QuestionSetQuestion questionModel : questionSetQuestions){
            try {
                questions.add(Question.fetch(questionModel.getID()));
            } catch (QuestionBuilderException e) {
                System.err.println("Something went wrong when fetching question " + questionModel.getID());
            }
        }
    }

    /**
     * Returns the underlying database model for this question set.
     * @return question set model
     */
    public QuestionSetModel getQuestionSetModel(){
        return data;
    }

    /**
     * Returns the languages for this question set.
     *
     * @return supported languages
     */
    public List<Language> getLanguages(){
        List<Language> languages = Language.listLanguages();
        for (int i = 0; i < questions.size(); i++){
            languages.retainAll(questions.get(i).getLanguages());
        }
        return languages;
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
        Ebean.update(data);
    }

    /**
     * Getter for competition field.
     *
     * @return the competition for this question set
     */
    public Competition getCompetition(){
        return new Competition(data.contest);
    }

    /**
     * Setter for the competition field.
     *
     * @param competition competition for this question set
     */
    public void setCompetition(Competition competition){
        data.contest = competition.getCompetitionModel();
        Ebean.update(data);
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
        Ebean.update(data);
    }

    /**
     * Gets the difficulty of a specific question.
     *
     * @param question the question wherefore the difficulty is asked
     * @return difficulty of the given question
     */
    public Difficulty getDifficulty(Question question){
        QuestionSetQuestion questionSetQuestion = Ebean.find(QuestionSetQuestion.class).where()
                .eq("qid", question.getID())
                .eq("qsid", Integer.parseInt(data.getID()))
                .findUnique();
        if (questionSetQuestion == null) return null;
        return questionSetQuestion.difficulty;
    }

    /**
     * Adds a new difficulty for the given question.
     *
     * @param question the question wherefore the difficulty is set
     * @param difficulty the new difficulty for the given question
     */
    public void setDifficulty(Question question, Difficulty difficulty){
        QuestionSetQuestion questionSetQuestion = Ebean.find(QuestionSetQuestion.class).where()
                .eq("qid", question.getID())
                .eq("qsid", Integer.parseInt(data.getID()))
                .findUnique();
        if (questionSetQuestion != null){
            questionSetQuestion.difficulty = difficulty;
            Ebean.update(questionSetQuestion);
        }
    }

    /**
     * Returns the list of questions
     * @return questions list
     */
    public List<Question> getQuestions() {
        return this.questions;
    }

    /**
     * Adds a new question to this question set
     * @param question question to be added
     */
    public void addQuestion(Question question){
        questions.add(question);
    }

    public boolean canActivate(){
        return true;
    }

    /**
     * Returns the id of this question set
     * @return question set id
     */
    public String getID() {
        return this.data.getID();
    }

    /**
     * Returns the underlying database model
     * @return Question set model
     */
    public QuestionSetModel getData() {
        return this.data;
    }

    /**
     * Returns the name of the question set
     * @return question set name
     */
    public String getName() {
        return data.name;
    }

}