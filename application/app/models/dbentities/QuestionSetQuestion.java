package models.dbentities;

import models.data.Difficulty;
import models.management.ManageableModel;

import javax.persistence.*;

/**
 * Database entity for question set questions.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsetquestions")
@IdClass(QuestionSetQuestion.QuestionSetQuestionPK.class)
public class QuestionSetQuestion extends ManageableModel {

    private static final long serialVersionUID = 2L;

    @ManyToOne
    @JoinColumn(name="qsid")
    public QuestionSetModel questionSet;

    @Id
    @ManyToOne
    @JoinColumn(name="qid")
    public int qid;

    @ManyToOne
    @JoinColumn(name="difficulty")
    public Difficulty difficulty;

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {"" + qid, questionSet.name, difficulty.name};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return Integer.toString(qid);
    }

    public class QuestionSetQuestionPK {

        private static final long serialVersionUID = 2L;

        public int qid;
        public String qsid;

        public QuestionSetQuestionPK(){}

        public QuestionSetQuestionPK(int qid,String qsid){
            this.qid = qid;
            this.qsid = qsid;
        }

        public int hashCode(){
            return super.hashCode();
        }

        public boolean equals(Object other){
            if(! (other instanceof QuestionSetQuestionPK))return false;
            QuestionSetQuestionPK oth = (QuestionSetQuestionPK) other;
            return this.qid==oth.qid && this.qsid.equals(oth.qsid);
        }
    }

}
