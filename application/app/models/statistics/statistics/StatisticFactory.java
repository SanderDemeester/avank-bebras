package models.statistics.statistics;

import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Creates statistics from strings. This is necessary because html pages won't
 * really store anything other then strings.
 * @author Felix Van der Jeugt
 */
public class StatisticFactory {

    private static StatisticFactory instance = null;

    /** Retrieve the singleton instance. */
    public static StatisticFactory instance() {
        if(instance == null) instance = new StatisticFactory();
        return instance;
    }

    private Map<String, Factory> map;

    private StatisticFactory() {
        map = new HashMap<String, Factory>();
        map.put(Gender.name, new Factory() {
            public Statistic create() { return new Gender(); }
        });
        map.put(Score.name, new Factory() {
            public Statistic create() { return new Score(); }
        });
        map.put(Age.name, new Factory() {
            public Statistic create() { return new Age(); }
        });
    }

    private static interface Factory {
        /** Creates a new Statistic of this factories kind. */
        public Statistic create();
    }

    /** Creates a new Statistic from the given type. */
    public Statistic create(String type) {
        return map.get(type).create();
    }

    /** Returns the set of all statistics (as type strings) this factory can
     * produce. */
    public Set<String> statistics() {
        return map.keySet();
    }

}
