package models.data.grades;

import java.lang.NumberFormatException;

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

    @Override public Grade createFromStrings(String... strings)
            throws CreationException {
        if(strings.length != 3) throw new CreationException(
                "Incorrect strings length.",
                "manager.error.fieldno"
        );
        removed = strings;
        if("".equals(strings[0].trim())) throw new CreationException(
                "Name field left empty.",
                "manager.error.empty"
        );
        int upper = 0, lower = 0;
        try {
            upper = Integer.parseInt(strings[1]);
            lower = Integer.parseInt(strings[2]);
        } catch(NumberFormatException e) {
            throw new CreationException(
                    "Not a number as bound.",
                    "manager.error.nan"
            );
        }
        return new Grade(strings[0], upper, lower);
    }

}
