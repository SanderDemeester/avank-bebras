package models.statistics;

import java.util.Map;
import java.util.HashMap;

import models.dbentities.UserModel;
import models.dbentities.ClassGroup;

/**
 * Factory for general populations.
 * @author Felix Van der Jeugt
 */
public class PopulationFactory {

    private static PopulationFactory instance = null;

    /**
     * Returns the singleton instance of this factory.
     */
    public static PopulationFactory instance() {
        if(instance == null) instance = new PopulationFactory();
        return instance;
    }

    /**
     * Creates a new Population with the identifier unique for that type of
     * population.
     * @param type The type of population.
     * @param identifier The type-unique identifier.
     * @return A new population.
     */
    public Population create(PopulationType type, String identifier) {
        return factories.get(type).create(identifier);
    }

    /**
     * Creates a new Population with the identifier unique for that type of
     * population.
     * @param type The type of population.
     * @param identifier The type-unique identifier.
     * @return A new population.
     */
    public Population create(String type, String identifier) {
        return create(types.get(type), identifier);
    }

    private final Map<PopulationType, TypePopulationFactory> factories;
    private final Map<String, PopulationType> types;

    private PopulationFactory() {

        factories = new HashMap<PopulationType, TypePopulationFactory>();
        factories.put(PopulationType.INDIVIDUAL, SinglePopulationFactory);
        factories.put(PopulationType.CLASS,      ClassPopulationFactory);
        /* insert new populations here */

        types = new HashMap<String, PopulationType>();
        types.put("INDIVIDUAL", PopulationType.INDIVIDUAL);
        types.put("CLASS",      PopulationType.CLASS);

    }


    /* Interface for a factory of populations. */
    private static interface TypePopulationFactory {

        /**
         * Creates a new Population with the identifier unique for that type of
         * population.
         * @param identifier The type-unique identifier.
         * @return A new population.
         */
        public Population create(String identifier);

    }

    /**
     * Factory for ClassPopulations.
     * @see models.statistics.ClassPopulation
     * @see models.statistics.PopulationFactory
     */
    private static class ClassPopulationFactory
            implements TypePopulationFactory {
        @Override public Population create(String identifier) {
            try {
                int id = Integer.parseInt(identifier);
                ClassGroup cg = Ebean.find(ClassGroup.class, identifier);
                return new ClassPopulation(cg);
            } catch(Exception e) {
                throw new PopulationFactoryException(e);
            }
        }
    }

    /**
     * Factory for SinglePopulations.
     * @see models.statistics.SinglePopulation
     * @see models.statistics.PopulationFactory
     */
    private static class SinglePopulationFactory implements PopulationFactory {
        @Override public Population create(String identifier) {
            try {
                UserModel um = Ebean.find(UserModel.class, identifier);
                return new SinglePopulation(um);
            } catch(Exception e) {
                throw new PopulationFactoryException(e);
            }
        }
    }
}
