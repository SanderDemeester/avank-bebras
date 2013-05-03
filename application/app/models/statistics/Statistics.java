package models.statistics;

import java.lang.Object;
import java.lang.Class;
import java.lang.RuntimeException;
import java.lang.ClassCastException;
import java.lang.reflect.Constructor;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;

import models.user.UserType;

import models.user.User;
import models.user.Independent;
import models.user.Teacher;
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

    /**
     * Creates a list of Populations the given user of the given type can view.
     */
    public static PopulationChooser getPopulationChooser(User user) {
        return genMap.get(user.getType()).choose(user);
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

                /* Single Users: All of them. */
                chooser.newType(
                    PopulationType.INDIVIDUAL,
                    mapToPop(
                        SinglePopulation.class,
                        UserModel.class,
                        Ebean.find(UserModel.class).findList()
                    )
                );

                /* Classes: All of them. */
                chooser.newType(
                    PopulationType.CLASS,
                    mapToPop(
                        ClassPopulation.class,
                        ClassGroup.class,
                        Ebean.find(ClassGroup.class).findList()
                    )
                );
                return chooser;
            }
        });
        genMap.put(UserType.ORGANIZER, genMap.get(UserType.ADMINISTRATOR));
        genMap.put(UserType.PUPIL_OR_INDEP, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                PopulationChooser chooser = new PopulationChooser();
                Independent indep = (Independent) user;

                /* Single Users: Well, himself. */
                chooser.newType(
                    PopulationType.INDIVIDUAL,
                    Arrays.asList((Population) new SinglePopulation(user.data))
                );

                /* Classes: Current and old. */
                List<ClassGroup> cgs = new LinkedList<ClassGroup>();

                // Adding current class if it exists.
                ClassGroup current = null;
                try { current = indep.getCurrentClass(); }
                catch (PersistenceException e) {}
                if(current != null) cgs.add(current);

                // Adding the old classes, if they exist.
                try { cgs.addAll(indep.getPreviousClasses()); }
                catch (PersistenceException e) {}

                chooser.newType(
                    PopulationType.CLASS,
                    mapToPop(
                        ClassPopulation.class,
                        ClassGroup.class,
                        cgs
                    )
                );
                return chooser;
            }
        });
        genMap.put(UserType.TEACHER, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                PopulationChooser chooser = new PopulationChooser();
                Teacher teacher = (Teacher) user;
                // Preparing some variables:
                List<ClassGroup> classes = new ArrayList<ClassGroup>();
                try {
                    classes.addAll(teacher.getClasses());
                    classes.addAll(teacher.getHelpClasses());
                } catch(PersistenceException e) {}

                /* Single Users: Himself and his students. */
                List<UserModel> users = Arrays.asList(user.data);
                for(ClassGroup cg : classes) {
                    try { users.addAll(cg.getPupils(ClassGroup.PupilSet.ALL)); }
                    catch (PersistenceException e) {}
                }
                chooser.newType(
                    PopulationType.INDIVIDUAL,
                    mapToPop(SinglePopulation.class, UserModel.class, users)
                );

                /* Classes: Just his classes. */
                chooser.newType(
                    PopulationType.CLASS,
                    mapToPop(ClassPopulation.class, ClassGroup.class, classes)
                );

                return chooser;
            }
        });
        genMap.put(UserType.ANON, new PopulationGenerator() {
            @Override public PopulationChooser choose(User user) {
                PopulationChooser chooser = new PopulationChooser();

                /* Single Users: None. */
                /* Classes: None. */
                return chooser;
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
