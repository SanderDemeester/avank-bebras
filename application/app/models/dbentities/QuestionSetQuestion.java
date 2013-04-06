package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Database entity for question set questions.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsetquestions")
public class QuestionSetQuestion {

    public String qsid;

    public String qid;

    public String difficulty;

}
