package models.statistics.populations;

import java.util.Map;
import java.util.HashMap;

/**
 * The different types of population.
 * @author Felix Van der Jeugt
 */
public class PopulationType {

    // TODO EMessages.
    public static PopulationType INDIVIDUAL = create(
            "INDIVIDUAL", "Single User", new SinglePopulation.Factory());
    public static PopulationType CLASS = create("CLASS", "Class",
            new ClassPopulation.Factory());

    // To keep track of instances.
    private static Map<String, PopulationType> instances;

    private static PopulationType create(String name, String message,
            Population.Factory factory) {
        if(instances == null) instances = new HashMap<String, PopulationType>();
        PopulationType n = new PopulationType(name, message, factory);
        instances.put(name, n);
        return n;
    }

    /**
     * Returns the PopulationType with the given name.
     */
    public static PopulationType getType(String name) {
        return instances.get(name);
    }

    private String name;
    private String message;
    private Population.Factory factory;

    /* Create a new PopulationType. */
    private PopulationType(String name, String message, Population.Factory f) {
        this.name = name;
        this.message = message;
        this.factory = f;
    }

    /** Returns the name of a type. */
    public String getName() {
        return this.name;
    }

    /** Returns the user message (tab title) for this type. */
    public String getMessage() {
        return this.message;
    }

    /** Returns the factory for this type. */
    public Population.Factory getFactory() {
        return this.factory;
    }

}
