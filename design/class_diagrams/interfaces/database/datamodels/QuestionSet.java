
package datamodels;

import enums.Level;

/**
 *
 * @author Jens N. Rammant
 */
public class QuestionSet implements Model {
    private String id;
    private Level level;
    private String contestID;

    public QuestionSet(String id, Level level, String contestID) {
        this.id = id;
        this.level = level;
        this.contestID = contestID;
    }

    public String getId() {
        return id;
    }

    public Level getLevel() {
        return level;
    }

    public String getContestID() {
        return contestID;
    }
    
    
}
