package models.statistics;

import java.util.List;

import models.dbentities.UserModel;

/**
 * Represents a group of Users, joined together to be summarized as a whole.
 * @author Felix Van der Jeugt
 */
public interface Population {

    /** Returns the Stringwise type of this population. */
    public String stringtype();

    /** Gets the id for this population as a string. */
    public String id();

    /**
     * Describes this population as a user readabl string. For instance: "Class
     * A of the Royal Institution".
     */
    public String describe();

    /**
     * Returns the list of users in this population.
     */
    public List<UserModel> getUsers();

}
