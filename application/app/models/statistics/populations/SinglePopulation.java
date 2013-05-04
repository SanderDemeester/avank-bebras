package models.statistics.populations;

import java.util.List;
import java.util.ArrayList;

import com.avaje.ebean.Ebean;

import models.dbentities.UserModel;

/**
 * Quite a stupid Population, actually. Represents a single user.
 * @author Felix Van der Jeugt
 */
public class SinglePopulation extends Population {

    private UserModel user;

    public SinglePopulation() {}

    public SinglePopulation(UserModel user) {
        this.user = user;
    }

    @Override public PopulationType populationType() {
        return PopulationType.INDIVIDUAL;
    }

    @Override public String id() {
        return user.id;
    }

    @Override public String describe() {
        return user.name + " (" + user.id + ")";
    }

    @Override public List<UserModel> getUsers() {
        List<UserModel> list = new ArrayList<UserModel>();
        list.add(user);
        return list;
    }

    /**
     * Factory for SinglePopulations.
     * @see models.statistics.SinglePopulation
     * @see models.statistics.Population
     */
    public static class Factory implements Population.Factory {
        @Override public Population create(String identifier)
                throws PopulationFactoryException {
            try {
                UserModel um = Ebean.find(UserModel.class, identifier);
                return new SinglePopulation(um);
            } catch(Exception e) {
                throw new PopulationFactoryException(e);
            }
        }
    }

}
