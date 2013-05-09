package models.statistics.statistics;

import java.lang.String;
import java.lang.Integer;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import models.statistics.populations.Population;

import models.dbentities.UserModel;

public class Gender extends DiscreteStatistic {

    @Override public String calculate(UserModel user) {
        return user.gender.toString();
    }

    @Override public String getName() {
        // TODO EMessage
        return "statistics.gender";
    }

}

