package models.statistics.statistics;

import java.lang.Double;
import java.lang.String;

import java.util.ArrayList;
import java.util.List;

import models.dbentities.UserModel;

import models.statistics.populations.Population;

public abstract class ContinuousStatistic extends Statistic {

    @Override public Summary calculate(Population data) {
        return null; // TODO
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

    public abstract Double calculate(UserModel user);

}
