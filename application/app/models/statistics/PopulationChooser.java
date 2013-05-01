package models.statistics;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import models.statistics.Population;
import models.statistics.ClassPopulation;
import models.statistics.SinglePopulation;

/**
 * This is a helper class for choosing populations. I propose a popup with tabs
 * for each type of population as view. Place a DMTV-like searcher on each tab.
 * @author Felix Van der Jeugt
 */
public class PopulationChooser {

    private Map<PopulationType, List<Population>> pops;

    public PopulationChooser() {
        pops = new HashMap<PopulationType, List<Population>>();
    }

    /**
     * Adds a new "tab" to the chooser.
     */
    public void newType(PopulationType t, List<Population> l) {
        pops.put(t, l);
    }

    /**
     * Filters the given list of Populations. This makes sure there are no
     * populations in the provided list, that cannot be selected with this
     * PopulationChooser.
     * @param list The list to be filtered.
     * @return The filtered list.
     */
    public List<Population> filter(List<Population> list) {
        List<Population> newlist = new ArrayList<Population>();
        for(Population p : list) {
            if(pops.get(p.populationType()).contains(p)) newlist.add(p);
        }
        return newlist;
    }

}
