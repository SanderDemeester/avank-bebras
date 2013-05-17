package models.statistics.statistics;

import java.lang.String;

import models.EMessages;
import models.dbentities.UserModel;

public class Language extends DiscreteStatistic {

    public static final String name = "statistics.statistics.language";

    @Override public String calculate(UserModel user) {
        return EMessages.get("languages." + user.preflanguage);
    }

    @Override public String getName() {
        return name;
    }

}

