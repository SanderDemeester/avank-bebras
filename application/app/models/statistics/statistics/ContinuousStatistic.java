package models.statistics.statistics;

import java.lang.Double;
import java.lang.String;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ArrayNode;

import play.libs.Json;

import models.EMessages;
import models.dbentities.UserModel;
import models.statistics.populations.Population;
import models.statistics.populations.SinglePopulation;

/**
 * A statistic that summarizes a population as a double. For instance, the
 * average score.
 * @author Felix Van der Jeugt
 */
public abstract class ContinuousStatistic extends Statistic {

    private static final int binMax = 40;

    @Override public ObjectNode asJson(Collection<Population> data) {

        /* Gathering some information about the data. */
        Map<String, List<Double>> map = new TreeMap<String, List<Double>>();
        double sum = 0;
        int n = 0;
        Double min = null;
        Double max = null;
        for(Population population : data) {
            List<Double> values = map.get(population.getColour());
            if(values == null) {
                values = new ArrayList<Double>();
                map.put(population.getColour(), values);
            }
            for(UserModel user : population.getUsers()) if(passes(user)) {
                Double x = calculate(user);
                if(x != null) {
                    sum += x;
                    n ++;
                    if(min == null || x < min) min = x;
                    if(max == null || x > max) max = x;
                    values.add(x);
                }
            }
        }

        /* In case there are no values, just return an empty json, which will
         * draw about exactly nothing. */
        double binCount = 0, binWidth = 0, average = 0;
        if(n != 0) {
        
            /* Calculation the optimal bin width. */
            average = sum / n;
            double m3 = 0, m2 = 0;
            for(List<Double> list : map.values()) for(Double value : list) {
                m2 += Math.pow(value - average, 2);
                m3 += Math.pow(value - average, 3);
            }
            m2 /= n;
            m3 /= n;
            if(m2 != 0) {
                double g1 = m3 / Math.pow(Math.sqrt(m2), 3.0);
                double sigma_g1 = Math.sqrt(6 * (n - 2) / (n + 1) / (n + 3));
                binCount = 1 + (Math.log(n)/Math.log(2)) +
                    (Math.log(1 + Math.abs(g1) / sigma_g1)/Math.log(2));
            } else {
                binCount = 1;
            }
            // You'd need a huge browser to view more, and we don't want the
            // binWith to get near zero.
            if(binCount > binMax || Double.isNaN(binCount)) binCount = Math.sqrt(n);
            binWidth = (max - min) / binCount;

        }

        /* Creating the Json object. */
        ObjectNode json = Json.newObject();

        /* Chart object */
        ObjectNode chart = Json.newObject();
        chart.put("defaultSeriesType", "column");
        json.put("chart", chart);

        /* The title. */
        ObjectNode title = Json.newObject();
        title.put("text", EMessages.get(getName()));
        json.put("title", title);

        /* The series object. */
        ArrayNode series = json.putArray("series");
        for(String colour : map.keySet()) {
            ObjectNode serie = Json.newObject();
            serie.put("name", colour);
            serie.put("color", colour);
            ArrayNode pairs = serie.putArray("data");
            
            /* Counting the bin values */
            //double[] bins = new double[(int)Math.ceil(binCount)];
            Map<Integer, Integer> bins = new TreeMap<Integer, Integer>();
            for(Double value : map.get(colour)) {
                int i = (int)((value - min) / binWidth);
                if(bins.get(i) == null) bins.put(i, 0);
                //bins[i] ++;
                bins.put(i, bins.get(i) + 1);
            }

            for(int i : bins.keySet()) {
                ArrayNode pair = pairs.addArray();
                pair.add(min + i * binWidth + binWidth / 2.0);
                pair.add(bins.get(i));
            }

            series.add(serie);
        }

        return json;
    }

    private List<Double> los = new ArrayList<Double>();
    private List<Double> ups = new ArrayList<Double>();

    @Override public boolean check(UserModel user) {
        // Update all interpreted conditions.
        if(los.size() < conditions().size()) {
            los.clear();
            ups.clear();
            for(String s : conditions()) {
                String[] bounds = s.split(",");
                los.add(Double.parseDouble(bounds[0]));
                ups.add(Double.parseDouble(bounds[1]));
            }
        }

        boolean between = false;
        Double value = calculate(user);
        if(value == null) return false; // Not calculatable is certainly not
                                        // between the given bounds.
        for(int i = 0; i < los.size() && !between; i++) {
            between = between || (los.get(i) <= value && value < ups.get(i));
        }
        return between;
    }

    /**
     * Calculates the value of this statistic for the given user. For instance,
     * returns the score of the student.
     */
    public abstract Double calculate(UserModel user);

}
