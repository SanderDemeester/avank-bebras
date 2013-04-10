package models.data.grades;

import models.data.Grade;
import models.data.manager.DataManager;

/**
 * @author Felix Van der Jeugt
 * @see models.data.manager.DataManager
 * @see models.data.Grade
 */
public class GradeManager extends DataManager<Grade> {

    @Override public String[] columns() {
        return new String[]{ "forms.name", "grades.lowerbound", "grades.upperbound" };
    }

    @Override public String url() {
        return "grades";
    }

    @Override public String title() {
        return "grades.title";
    }

    @Override public Class<Grade> getTClass() {
        return Grade.class;
    }

    @Override public Grade createFromStrings(String... strings) {
        return new Grade(
            strings[0],
            Integer.parseInt(strings[1]),
            Integer.parseInt(strings[2])
        );
    }

}
