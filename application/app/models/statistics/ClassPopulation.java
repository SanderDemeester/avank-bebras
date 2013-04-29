package models.statistics;

import java.util.List;
import java.util.ArrayList;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model.Finder;

import models.statistics.Population;
import models.dbentities.ClassGroup;
import models.dbentities.UserModel;
import models.dbentities.ClassPupil;

/**
 * A Population of one class.
 * @author Felix Van der Jeugt
 */
public class ClassPopulation implements Population {

    private ClassGroup classGroup;

    public ClassPopulation() {}

    public ClassPopulation(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    @Override public String id() {
        return "" + classGroup.id;
    }

    @Override public String describe() {
        return classGroup.name + " of the " + classGroup.schoolid;
    }

    @Override public List<UserModel> getUsers() {
        List<UserModel> list = Ebean.find(UserModel.class).where()
                .eq("classid", classGroup.id).findList();
        if(list != null && list.size() > 0) return list;
        list = new ArrayList<UserModel>();
        for(ClassPupil cp : Ebean.find(ClassPupil.class).where()
                .eq("classID", classGroup.id).findList()) {
            list.add(Ebean.find(UserModel.class, cp.classid));
        }
        return list;
    }

}
