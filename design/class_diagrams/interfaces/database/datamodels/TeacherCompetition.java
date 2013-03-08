
package datamodels;

/**
 *
 * @author Jens N. Rammant
 */
public class TeacherCompetition {
    private String teacherID;
    private String competitionID;

    public TeacherCompetition(String teacherID, String competitionID) {
        this.teacherID = teacherID;
        this.competitionID = competitionID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public String getCompetitionID() {
        return competitionID;
    }
    
    
}
