package models.statistics.statistics;

import java.lang.String;
import java.lang.Double;

import java.util.List;
import java.util.ArrayList;

import util.CSVWriter;

import models.statistics.populations.Population;
import models.EMessages;

/**
 * Summarizes a bit of continuous data.
 */
public class ContinuousSummary implements Summary {

    private String statistic;
    private List<Population> populations;
    private List<Double> values;

    /**
     * Creates a new summary for continuous data.
     * @param statistic The name of the statistics yielding this summary, for
     * instance "mean of scores"
     */
    public ContinuousSummary(String statistic) {
        this.statistic = statistic;
        this.populations = new ArrayList<Population>();
        this.values = new ArrayList<Double>();
    }

    /**
     * Adds data to this summary.
     * @param population The population that was summarized.
     * @param value The value the statistic measured for the population.
     */
    public void addData(Population population, Double value) {
        populations.add(population);
        values.add(value);
    }

    @Override public String asPlotData() {
        return null; // TODO
    }

    @Override public String asCsvFile() {
        CSVWriter writer = new CSVWriter();

        // Write csv titles.
        writer.addLine(
            EMessages.get("statistics.csv.population"),
            EMessages.get("statistics.csv.populationgroup"),
            EMessages.get(statistic)
        );

        // Write data pairs.
        for(int i = 0; i < populations.size(); i++) writer.addLine(
            populations.get(i).describe(),
            populations.get(i).getColour(),
            String.valueOf(values.get(i))
        );

        return writer.asString();
    }

}
