package models.statistics.statistics;

import java.lang.String;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

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


