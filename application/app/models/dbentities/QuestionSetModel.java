package models.dbentities;

import models.competition.Competition;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Language;
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
@Table(name="questionsets")
public class QuestionSetModel extends Model {

    @Id
    public String id;

    public Grade grade;
    public Map<Question, Difficulty> difficulties;
    public boolean active;
    public Competition competition;
    public Set<Question> questions;

    @Transient
    public List<Language> languages;

}
