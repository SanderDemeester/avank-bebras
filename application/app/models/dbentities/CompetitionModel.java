package models.dbentities;

import models.competition.CompetitionType;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Database entity for competition.
 *
 * @author Kevin Stobbelaar.
 *
 */
@Entity
@Table(name="contests")
public class CompetitionModel extends Model {
    private static final long serialVersionUID = 2L;

    @Id
    public String id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    @Enumerated(EnumType.STRING)
    public CompetitionType type;

    @Constraints.Required
    public boolean active;

    @Constraints.Required
    public Date starttime;

    @Constraints.Required
    public Date endtime;
    
    @Constraints.Required
    public String creator;

}
