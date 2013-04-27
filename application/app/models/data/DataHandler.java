
package models.data;

import java.util.List;

import models.data.Grade;
import models.data.Link;
import models.data.Difficulty;
import models.data.grades.GradeManager;
import models.data.links.LinkManager;
import models.data.difficulties.DifficultyManager;

/**
 * Stub class for DataHandler.
 * right now the data is hardcoded, this later be replaced by database data.
 *
 * @author Felix Van der Jeugt
 */
public class DataHandler {

    private static GradeManager      gm = new GradeManager();
    private static LinkManager       lm = new LinkManager();
    private static DifficultyManager dm = new DifficultyManager();

    /**
     * Returns the different Grades users can belong to.
     * @return List of grades.
     */
    public static List<Grade> getGrades() {
        return gm.list();
    }

    /**
     * Returns the different difficulties a question can have.
     * @return Array of difficulties.
     */
    public static List<Difficulty> getDifficulties() {
        return dm.list();
    }

    /**
     * Returns the links to show on the home page.
     * @return Links for the home page.
     */
    public static List<Link> getLinks() {
        return lm.list();
    }

}
