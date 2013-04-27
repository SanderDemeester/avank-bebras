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
        return new String[]{ "forms.name", "difficulties.numerical",
            "difficulties.correct", "difficulties.wrong", "difficulties.open" };
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
        if(strings.length != 5) throw new CreationException(
                "Incorrect strings length.",
                "manager.error.fieldno"
        );
        removed = strings;
        if("".equals(strings[0].trim())) throw new CreationException(
                "Name field left empty.",
                "manager.error.empty"
        );
        int[] ints = new int[strings.length - 1];
        try {
            for(int i = 1; i < strings.length; i++) {
                ints[i - 1] = Integer.parseInt(strings[i]);
            }
        } catch(NumberFormatException e) {
            throw new CreationException(
                    "Not a number.",
                    "manager.error.nan"
            );
        }
        return new Difficulty(strings[0], ints[0], ints[1], ints[2], ints[3]);
    }

}
