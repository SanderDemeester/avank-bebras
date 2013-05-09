package models.statistics.statistics;

import java.lang.Double;
import java.lang.String;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import models.dbentities.UserModel;

import models.statistics.populations.Population;
import models.statistics.populations.SinglePopulation;

/**
 * A statistic that summarizes a population as a double. For instance, the
 * average score.
 * @author Felix Van der Jeugt
 */
public abstract class ContinuousStatistic extends Statistic {

    @Override public Summary calculate(Collection<Population> data) {
        ContinuousSummary summary = new ContinuousSummary(getName());
        for(Population population : data) {
            Double value = calculate(population);
            if(value != null) summary.addData(population, value);
        }
        return summary;
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
        for(int i = 0; i < los.size() && !between; i++) {
            between = between || (los.get(i) <= value && value < ups.get(i));
        }
        return between;
    }

    private Double calculate(UserModel user) {
        return calculate(new SinglePopulation(user));
    }

    /**
     * Calculate this statictic for the given population. For instance, returns
     * the average score of all pupils in the population.
     */
    public abstract Double calculate(Population population);

}
