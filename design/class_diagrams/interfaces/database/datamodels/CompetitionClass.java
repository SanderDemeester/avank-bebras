
package datamodels;

/**
 *
 * @author Jens N. Rammant
 */
public class CompetitionClass implements Model{
    private String competitionID;
    private String classID;

    public CompetitionClass(String competitionID, String classID) {
        this.competitionID = competitionID;
        this.classID = classID;
    }

    public String getCompetitionID() {
        return competitionID;
    }

    public String getClassID() {
        return classID;
    }
    
    
}
