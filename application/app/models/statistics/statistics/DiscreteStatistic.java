package models.statistics.statistics;

import java.lang.String;

import models.statistics.populations.Population;

import models.dbentities.UserModel;

public abstract class DiscreteStatistic extends Statistic {

    @Override public Summary calculate(Population data) {
        return null; // TODO
    }

    @Override public boolean check(UserModel user) {
        return conditions().contains(calculate(user));
    }

    public abstract String calculate(UserModel user);

}
