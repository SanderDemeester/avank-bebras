package models.statistics.statistics;

import java.lang.String;
import java.lang.Integer;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import models.statistics.populations.Population;

import models.dbentities.UserModel;

public abstract class DiscreteStatistic extends Statistic {

    @Override public Summary calculate(Collection<Population> data) {
        DiscreteSummary summary = new DiscreteSummary(getName());
        Integer value;
        String key;
        for(Population population : data) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            for(UserModel user : population.getUsers()) {
                key = calculate(user);
                value = map.get(key);
                if(value == null) value = 0;
                map.put(key, value + 1);
            }
            summary.addData(population, map);
        }
        return summary;
    }

    @Override public boolean check(UserModel user) {
        return conditions().contains(calculate(user));
    }

    public abstract String calculate(UserModel user);

}
