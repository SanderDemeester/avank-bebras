package models.dbentities;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String id;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String type;

    @Constraints.Required
    private boolean active;

    @Formats.DateTime(pattern = "yyyy/dd/mm")
    private Date starttime;

    @Formats.DateTime(pattern = "yyyy/dd/mm")
    private Date endtime;

}
