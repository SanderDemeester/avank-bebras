
package datamodels;

import enums.Level;
import java.util.Date;

/**
 *
 * @author Jens N. Rammant
 */
public class ClassGroup {

    private String id;
    private String name;
    private Date expirationDate;
    private String schoolID;
    private String teacherID;
    private Level level;

    public ClassGroup(String id, String name, Date expirationDate, String schoolID, String teacherID, Level level) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.schoolID = schoolID;
        this.teacherID = teacherID;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public Level getLevel() {
        return level;
    }
    
    
}
