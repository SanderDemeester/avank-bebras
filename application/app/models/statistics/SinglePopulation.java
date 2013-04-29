package models.statistics;

import java.util.List;
import java.util.ArrayList;

import models.statistics.Population;
import models.dbentities.UserModel;

/**
 * Quite a stupid Population, actually. Represents a single user.
 * @author Felix Van der Jeugt
 */
public class SinglePopulation implements Population {

    private UserModel user;

    public SinglePopulation() {}

    public SinglePopulation(UserModel user) {
        this.user = user;
    }

    @Override public String stringtype() {
        return "INDIVIDUAL";
    }

    @Override public String id() {
        return user.id;
    }

    @Override public String describe() {
        return user.name + "(" + user.id + ")";
    }

    @Override public List<UserModel> getUsers() {
        List<UserModel> list = new ArrayList<UserModel>();
        list.add(user);
        return list;
    }

}
