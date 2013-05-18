package models.dbentities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.validation.NotNull;
/**
 * The answers for a competition by an authenticated user
 * @author Ruben Taelman
 *
 */
@Entity
@Table(name="pupilanswers")
public class PupilAnswer extends Model implements AnswerModel{

    private static final long serialVersionUID = 1L;

    /**
     * pupil id
     */
    @Id
    public String indid;

    /**
     * question
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name="qid")
    public QuestionModel question;

    /**
     * answer
     */
    public String answer;

    /**
     * is correct
     */
    public boolean correct;

    /**
     * language code
     */
    public String language;

    /**
     * question set
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name="questionsetid")
    public QuestionSetModel questionset;

    /**
     * make new pupil answer
     * @param user user
     */
    public PupilAnswer(UserModel user) {
        this.indid = user.id;
    }

    @Override
    public int getID() {
        // Unsupported, ebean support for joined id's is kinda non-existing
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

    @Override
    public void save() {
        // If an old answer exists, overwrite it
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("indid", this.indid);
        map.put("qid", this.question.id);
        map.put("questionsetid", this.questionset.id);

        PupilAnswer answer = Ebean.find(PupilAnswer.class).where().allEq(map).endJunction().findUnique();
        if(answer != null) answer.delete();
        super.save();
    }
}
