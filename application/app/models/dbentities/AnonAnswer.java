package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

import play.db.ebean.Model;
/**
 * The answers for a competition by an anonymous user
 * @author Ruben Taelman
 *
 */
@Entity
@Table(name="anonanswer")
public class AnonAnswer extends Model implements AnswerModel{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anonanswer_id_seq")
    public int id;
    
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

    @Override
    public int getID() {
        return this.id;
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
