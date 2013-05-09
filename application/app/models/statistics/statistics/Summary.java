package models.statistics.statistics;

/**
 * Summarizes a bit of data from s statistic.
 * @author Felix Van der Jeugt
 */
public interface Summary {

    /**
     * Returns this Summary as a String of data that can be plot.
     */
    public String asPlotData();

    /**
     * Returns a string formed as a csv file, about the data in this summary.
     */
    public String asCsvFile();

}
