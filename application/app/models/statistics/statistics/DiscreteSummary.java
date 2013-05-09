package models.statistics.statistics;

import java.lang.String;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import util.CSVWriter;

import models.EMessages;
import models.statistics.populations.Population;

/**
 * Summarizes a bit of discrete data.
 * @author Felix Van der Jeugt
 */
public class DiscreteSummary implements Summary {

    private String statistic;
    private List<Population> populations;
    private List<Map<String, Integer>> values;

    /**
     * Creates a new summary for discrete data.
     * @param statistic The name of the statistics yielding this summary, for
     * instance "gender"
     */
    public DiscreteSummary(String statistic) {
        this.statistic = statistic;
        this.populations = new ArrayList<Population>();
        this.values = new ArrayList<Map<String, Integer>>();
    }

    /**
     * Adds data to this summary.
     * @param population The population that was summarized
     * @param counts The possible values and their counts.
     */
    public void addData(Population population, Map<String, Integer> counts) {
        populations.add(population);
        values.add(counts);
    }

    @Override public String asPlotData() {
        return null; // TODO
    }

    @Override public String asCsvFile() {
        CSVWriter writer = new CSVWriter();

        // Write csv titles.
        writer.addLine(
            EMessages.get("statistics.csv.population"),
            EMessages.get(statistic),
            EMessages.get("statistics.csv.count")
        );

        // Write data pairs.
        for(int i = 0; i < populations.size(); i++) {
            for(String s : values.get(i).keySet()) writer.addLine(
                populations.get(i).describe(),
                s,
                String.valueOf(values.get(i).get(s))
            );
        }

        return writer.asString();
    }

}
