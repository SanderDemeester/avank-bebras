
package datamodels;

/**
 *
 * @author Jens N. Rammant
 */
public class OrganizerCompetition implements Model{
    private String organizerID;
    private String CompetitionID;

    public OrganizerCompetition(String organizerID, String CompetitionID) {
        this.organizerID = organizerID;
        this.CompetitionID = CompetitionID;
    }

    public String getOrganizerID() {
        return organizerID;
    }

    public String getCompetitionID() {
        return CompetitionID;
    }
    
    
}
