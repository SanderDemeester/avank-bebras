package models.dbentities;

import models.competition.CompetitionType;
import play.data.format.Formats;
import play.data.validation.Constraints;

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
public class CompetitionModel {

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
    @Formats.DateTime(pattern = "yyyy/dd/mm")
    public Date starttime;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy/dd/mm")
    public Date endtime;

}
