package models.dbentities;

import models.competition.Competition;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Language;
import models.management.Manageable;
import models.question.Question;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Question set entity.
 *
 * @author Kevin Stobbelaar
 */
@Entity
@Table(name="questionsetstest")
public class QuestionSetModel extends Model implements Manageable {

    @Id
    public String id;

    @Transient
    public Grade grade;
    @Transient
    public Map<Question, Difficulty> difficulties;
    @Transient
    public boolean active;
    @Transient
    public Competition competition;
    @Transient
    public Set<Question> questions;
    @Transient
    public List<Language> languages;

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {id};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }
}
