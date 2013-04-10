package models.data.difficulties;

import models.data.Difficulty;
import models.data.manager.DataManager;

/**
 * @author Felix Van der Jeugt
 * @see models.data.manager.DataManager
 * @see models.data.Difficulty
 */
public class DifficultyManager extends DataManager<Difficulty> {

    @Override public String[] columns() {
        return new String[]{ "forms.name", "difficulties.numerical" };
    }

    @Override public String url() {
        return "difficulties";
    }

    @Override public String title() {
        return "difficulties.title";
    }

    @Override public Class<Difficulty> getTClass() {
        return Difficulty.class;
    }

    @Override public Difficulty createFromStrings(String... strings)
            throws CreationException {
        if(strings.length != 2) throw new CreationException(
                "Incorrect strings length.",
                "manager.error.fieldno"
        );
        if("".equals(strings[0])) throw new CreationException(
                "Name field left empty.",
                "manager.error.empty"
        );
        int order = 0;
        try {
            order = Integer.parseInt(strings[1]);
        } catch(NumberFormatException e) {
            throw new CreationException(
                    "Not a number as bound.",
                    "manager.error.nan"
            );
        }
        return new Difficulty(strings[0], order);
    }

}
