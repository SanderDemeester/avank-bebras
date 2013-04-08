package models.dbentities;

import models.competition.Competition;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Language;
import models.management.ManageableModel;
import models.question.Question;
import play.data.validation.Constraints.Required;
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
public class QuestionSetModel extends ManageableModel {
    private static final long serialVersionUID = 1L;

    @Id
    public String id;

    public String level;
    public boolean active;
    @Required
    public String name;
    public String contid;

    @Transient
    public Grade grade;
    @Transient
    public Map<Question, Difficulty> difficulties;
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
    public String[] getFieldValues() {
        String[] result = {name};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    public String getID() {
        return id;
    }

    /**
     * Returns the name of the object.
     *
     * @return name
     */
    public String getTableName() {
        return name;
    }
}
