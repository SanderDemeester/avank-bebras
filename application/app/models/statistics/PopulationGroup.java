package models.statistics;

import java.util.List;
import java.util.ArrayList;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;

import models.statistics.Population;
import models.statistics.Colour;

/**
 * This class represents a group of populations. These populations are viewed
 * together in the plots, as data of the same colour. When you download the
 * data, the populationgroup is added as an extra field.
 * @author Felix Van der Jeugt
 */
public class PopulationGroup {

    /** The colour associated with this group. */
    @Required public Colour colour;

    /** The Populations included in this group. */
    @Valid public List<Population> populations;

    /** Create a new empty PopulationGroup. */
    public PopulationGroup() {}

    /**
     * Create a new PopulationGroup with the given Colour including the
     * Populations given as parameters.
     * @param colour The color for this group.
     * @param populations The populations this PopulationGroup should include.
     */
    public PopulationGroup(Colour colour, Population... populations) {
        this.colour = colour;
        this.populations = new ArrayList<Population>();
        for(Population population : populations) {
            this.populations.add(population);
        }
    }

}
