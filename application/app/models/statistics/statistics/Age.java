package models.statistics.statistics;

import java.lang.Double;
import java.util.Date;


import models.dbentities.UserModel;

public class Age extends ContinuousStatistic {

    public static final String name = "statistics.statistics.age";
    private static final double MILLIS = 365.242 * 24 * 60 * 60 * 1000;

    @Override public Double calculate(UserModel user) {
        return ((new Date()).getTime() - user.birthdate.getTime()) / MILLIS;
    }

    @Override public String getName() {
        return name;
    }

}


