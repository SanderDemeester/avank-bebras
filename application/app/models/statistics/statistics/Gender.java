package models.statistics.statistics;

import java.lang.String;

import models.EMessages;
import models.dbentities.UserModel;

public class Gender extends DiscreteStatistic {

    public static final String name = "statistics.statistics.gender";

    @Override public String calculate(UserModel user) {
        return EMessages.get("register." + user.gender.toString());
    }

    @Override public String getName() {
        return name;
    }

}

