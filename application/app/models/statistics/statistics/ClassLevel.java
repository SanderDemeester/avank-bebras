package models.statistics.statistics;

import java.lang.String;
import java.lang.Integer;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.statistics.populations.Population;
import models.dbentities.UserModel;
import models.dbentities.ClassGroup;

public class ClassLevel extends DiscreteStatistic {

    public static final String name = "statistics.statistics.classlevel";

    @Override public String calculate(UserModel user) {
        if(user.classgroup == null) return null;
        ClassGroup classGroup = null;
        try {
            classGroup = Ebean.find(ClassGroup.class, user.classgroup);
        } catch(PersistenceException e) {
            return null;
        }
        return classGroup.level;
    }

    @Override public String getName() {
        return name;
    }

}


