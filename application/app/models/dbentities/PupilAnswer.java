package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

import com.avaje.ebean.validation.NotNull;
/**
 * The answers for a competition by an authenticated user
 * @author Ruben Taelman
 *
 */
@Entity
@Table(name="pupilanswer")
public class PupilAnswer extends Model implements AnswerModel{

    private static final long serialVersionUID = 1L;
    
    @Id
    /*@ManyToOne
    @NotNull
    @JoinColumn(name="indid")*/
    public String indid;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name="questionid")
    public QuestionModel question;
    
    public String answer;
    
    public boolean correct;
    
    public String language;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name="questionsetid")
    public QuestionSetModel questionset;
    
    public PupilAnswer(UserModel user) {
        this.indid = user.id;
    }

    @Override
    public int getID() {
        // TODO: joined id?
        return 0;
    }

    @Override
    public int getQuestionID() {
        return this.question.id;
    }

    @Override
    public int getQuestionSetID() {
        return this.questionset.id;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }

    @Override
    public boolean isCorrect() {
        return this.correct;
    }

    @Override
    public String getLanguageCode() {
        return this.language;
    }
    
    @Override
    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    @Override
    public void setQuestionSet(QuestionSetModel questionSet) {
        this.questionset = questionSet;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public void setLanguageCode(String languageCode) {
        this.language = languageCode;
    }
}
