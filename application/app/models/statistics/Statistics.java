package models.statistics;

import java.lang.Object;
import java.lang.Class;
import java.lang.RuntimeException;
import java.lang.ClassCastException;
import java.lang.reflect.Constructor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import com.avaje.ebean.Ebean;

import models.user.UserType;

import models.user.User;
import models.statistics.SinglePopulation;
import models.statistics.ClassPopulation;
import models.statistics.Population;
import models.statistics.PopulationChooser;
import models.dbentities.UserModel;
import models.dbentities.ClassGroup;

/**
 * Remembers the links between users, their user type, and the kinds of
 * populations they can observe.
 * @author Felix Van der Jeugt
 */
public class Statistics {

    /**
     * Creates a list of Populations the given user of the given type can view.
     */
    public static PopulationChooser getPopulationChooser(UserType type, User user) {
        return genMap.get(type).choose(user);
    }

    private static interface PopulationGenerator {
        /**
         * Lists the viewable populations for this user. Note that for
         * Anonymous users, the parameter may be null, as the populations are
         * the same for each of them.
         * @param user The current user.
         */
        public PopulationChooser choose(User user);
    }

    private static Map<UserType, PopulationGenerator> genMap =
        new HashMap<UserType, PopulationGenerator>();
    static {
        genMap.put(UserType.ADMINISTRATOR, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                PopulationChooser chooser = new PopulationChooser();
                chooser.newType(
                    SinglePopulation.class,
                    mapToPop(
                        SinglePopulation.class,
                        UserModel.class,
                        Ebean.find(UserModel.class).findList()
                    )
                );
                chooser.newType(
                    ClassPopulation.class,
                    mapToPop(
                        ClassPopulation.class,
                        ClassGroup.class,
                        Ebean.find(ClassGroup.class).findList()
                    )
                );
                return chooser;
            }
        });
        genMap.put(UserType.ORGANIZER, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                return null; // TODO
            }
        });
        genMap.put(UserType.INDEPENDENT, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                return null; // TODO
            }
        });
        genMap.put(UserType.PUPIL, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                return null; // TODO
            }
        });
        genMap.put(UserType.TEACHER, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                return null; // TODO
            }
        });
        genMap.put(UserType.ANON, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                return null; // TODO
            }
        });
    }

    // Help function. Takes a list of Objects a class b, which can by passed to
    // a constructor of class a as only parameter. Returns the populations
    // created.
    //
    // for instance:
    // mapToPop(ClassPopulation.class, ClassGroup.class, listOfClassGroups)
    private static List<Population> mapToPop(Class<? extends Population> a,
            Class<?> b, List<?> l) {
        List<Population> pops = new ArrayList<Population>(l.size());

        // Get constructor.
        Constructor<? extends Population> c;
        try { c = a.getConstructor(b); }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Population subclasses should have a "
                    + "constructor which takes a single parameter.");
        }
        catch (SecurityException e) {
            throw new RuntimeException("Dammit, make your constructors public, "
                    + "would you?");
        }

        // Create list.
        Iterator<?> it = l.iterator();
        try {
            while(it.hasNext()) pops.add(c.newInstance(it.next()));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pops;
    }

}
