package models.statistics.populations;

import java.util.List;

import models.dbentities.UserModel;

/**
 * Represents a group of Users, joined together to be summarized as a whole.
 * @author Felix Van der Jeugt
 */
public abstract class Population {

    private String colour = null;

    /** Returns the type of this population. */
    public abstract PopulationType populationType();

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

    @Override public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Population)) return false;
        Population that = (Population) o;
        return (
            this.id().equals(that.id()) &&
            this.populationType().equals(that.populationType())
        );
    }

    /* ====================================================================== *\
     * The interface for Population Factories.
    \* ====================================================================== */
    public static interface Factory {

        /**
         * Creates a new Population with the identifier unique for that type of
         * population.
         * @param identifier The type-unique identifier.
         * @return A new population.
         */
        public Population create(String identifier)
            throws PopulationFactoryException;

    }

}
