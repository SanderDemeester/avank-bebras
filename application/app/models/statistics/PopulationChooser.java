package models.statistics;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import models.statistics.Population;
import models.statistics.ClassPopulation;
import models.statistics.SinglePopulation;

/**
 * This is a helper class for choosing populations. I propose a popup with tabs
 * for each type of population as view. Place a DMTV-like searcher on each tab.
 * @author Felix Van der Jeugt
 */
public class PopulationChooser {

    private Map<Class<? extends Population>, List<Population>> pops;

    public PopulationChooser() {
        pops = new HashMap<Class<? extends Population>, List<Population>>();
    }

    /**
     * Adds a new "tab" to the chooser.
     */
    public void newType(Class<? extends Population> c, List<Population> l) {
        pops.put(c, l);
    }

}
