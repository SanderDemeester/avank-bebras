package models.question;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.competition.Competition;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Language;

/**
 *
 * @author Ruben Taelman
 *
 */
public class QuestionSet{
    private List<Question> questions = new LinkedList<Question>();
    private boolean active;
    private Competition competition;
    private Grade grade;
    private Map<Question, Difficulty> difficulties;

    public List<Language> getLanguages() {
        return null;
    }

    public boolean canActivate() {
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Difficulty getDifficulty() {
        return null;
    }

    public void setDifficulty(Difficulty difficulty) {

    }
    
    public List<Question> getQuestions() {
        return this.questions;
    }
    
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

}
