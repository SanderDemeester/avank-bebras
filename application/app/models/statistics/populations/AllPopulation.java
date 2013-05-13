package models.statistics.populations;

import java.util.List;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.dbentities.UserModel;

/**
 * Represents simply all users in the system.
 * @author Felix Van der Jeugt
 */
public class AllPopulation extends Population {

    @Override public PopulationType populationType() {
        return PopulationType.ALL;
    }

    @Override public String id() {
        return ""; // Doesn't really require an id, there's only one everyone.
    }

    @Override public String describe() {
        return EMessages.get("statistics.populations.all");
    }

    @Override public List<UserModel> getUsers() {
        return Ebean.find(UserModel.class).findList();
    }

    /** @see models.statistics.populations.Population.Factory */
    public static class Factory implements Population.Factory {
        @Override public Population create(String identifier) {
            return new AllPopulation();
        }
    }

}
