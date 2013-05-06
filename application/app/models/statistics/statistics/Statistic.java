package models.statistics.statistics;

import java.lang.String;

import java.util.Collection;
import java.util.ArrayList;

import models.dbentities.UserModel;
import models.statistics.populations.Population;

/**
 * This interface represents a statistic on users. It can take a population and
 * turn it into a plot or csv-file.
 * @author Felix Van der Jeugt
 */
public abstract class Statistic {

    private Collection<Statistic> filters = new ArrayList<Statistic>();
    private Collection<String> conditions = new ArrayList<String>();

    public abstract Summary calculate(Population population);

    public void addFilters(Statistic... filters) {
        for(Statistic f : filters) this.filters.add(f);
    }

    public abstract boolean check(UserModel user);

    public void addConditions(String... conditions) {
        for(String s : conditions) this.conditions.add(s);
    }

    protected Collection<String> conditions() {
        return conditions;
    }

}
