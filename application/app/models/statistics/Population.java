package models.statistics;

import java.util.List;

import models.dbentities.UserModel;

/**
 * Represents a group of Users, joined together to be summarized as a whole.
 * @author Felix Van der Jeugt
 */
public abstract class Population {

    private String colour;

    /** Returns the Stringwise type of this population. */
    public abstract String stringtype();

    /** The colour this population should be displayed in. The colour should be
     * in an html readable format. */
    public String getColour() {
        return this.colour;
    }

    /** Sets the colour this population should be displayed in. The colour
     * should be in an html readable format. */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /** Gets the id for this population as a string. */
    public abstract String id();

    /**
     * Describes this population as a user readabl string. For instance: "Class
     * A of the Royal Institution".
     */
    public abstract String describe();

    /**
     * Returns the list of users in this population.
     */
    public abstract List<UserModel> getUsers();

}
