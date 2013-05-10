package models.statistics.statistics;

import java.lang.String;
import java.lang.Integer;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ArrayNode;

import play.libs.Json;

import models.EMessages;
import models.statistics.populations.Population;
import models.dbentities.UserModel;

public abstract class DiscreteStatistic extends Statistic {

    @Override public ObjectNode asJson(Collection<Population> data) {
        Integer value;
        String key;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(Population population : data) {
            for(UserModel user : population.getUsers()) if(passes(user)) {
                key = calculate(user);
                value = map.get(key);
                if(value == null) value = 0;
                map.put(key, value + 1);
            }
        }

        /* Creating the Json object. */
        ObjectNode json = Json.newObject();

        /* Chart object */
        ObjectNode chart = Json.newObject();
        chart.put("defaultSeriesType", "pie");
        json.put("chart", chart);

        /* The title. */
        ObjectNode title = Json.newObject();
        title.put("text", EMessages.get(getName()));
        json.put("title", title);

        /* The series object. */
        ArrayNode series = json.putArray("series");
        ObjectNode serie = Json.newObject();
        serie.put("name", EMessages.get("statistics.count"));
        ArrayNode pairs = serie.putArray("data");
        for(String str : map.keySet()) {
            ArrayNode pair = pairs.addArray();
            pair.add(str);
            pair.add(map.get(str));
        }
        series.add(serie);

        return json;
    }

    @Override public boolean check(UserModel user) {
        return conditions().contains(calculate(user));
    }

    public abstract String calculate(UserModel user);

}
