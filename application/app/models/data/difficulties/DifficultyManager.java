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

    @Override public Difficulty createFromStrings(String... strings) {
        return new Difficulty(strings[0], Integer.parseInt(strings[1]));
    }

}
