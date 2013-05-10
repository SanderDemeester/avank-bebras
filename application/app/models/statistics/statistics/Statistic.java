package models.statistics.statistics;

import java.lang.String;

import java.util.Collection;
import java.util.ArrayList;

import org.codehaus.jackson.node.ObjectNode;

import models.dbentities.UserModel;
import models.dbentities.QuestionModel;
import models.dbentities.QuestionSetModel;
import models.statistics.populations.Population;

/**
 * This interface represents a statistic on users. It can take a population and
 * turn it into a plot or csv-file. The type parameter T represents the smallest
 * grain of data, like a user or a question, this statistic works on.
 * @author Felix Van der Jeugt
 */
public abstract class Statistic {

    private Collection<Statistic> filters = new ArrayList<Statistic>();
    private Collection<String> conditions = new ArrayList<String>();

    /**
     * Return a Json object of the data, ready for plotting with HighCharts.
     * @param data The populations to be processed.
     * @return A HighCharts Json object.
     */
    public abstract ObjectNode asJson(Collection<Population> data);

    /**
     * Add a filter to the statistic. Users who do not pass anny of the given
     * statistical filters will not be included in the summary of this
     * statistic.
     * @param filters The filters to be added.
     */
    public void addFilters(Statistic... filters) {
        for(Statistic f : filters) this.filters.add(f);
    }

    /**
     * Lists all the filters on this statistic.
     */
    public Collection<Statistic> filters() {
        return filters;
    }

    /**
     * If this statistic is used as a filter, this function checks the given
     * object against it's conditions.
     * @param user The user object to be checked.
     * @return Whether or not he passes.
     */
    public abstract boolean check(UserModel user);

    /**
     * Add conditions, for when this statistic is used as a filter.
     * @param conditions The conditions to add.
     */
    public void addConditions(String... conditions) {
        for(String s : conditions) this.conditions.add(s);
    }

    /**
     * Returns the collection of conditions.
     */
    public Collection<String> conditions() {
        return conditions;
    }

    /**
     * Returns the name of this statistic, possibly an EMessage parameter.
     */
    public abstract String getName();

    /**
     * Sets the question for this statistic. Most statistics ignore this, but
     * some question dependend statistics can use it.
     */
    public void setQuestion(QuestionModel question) {
    }

    /**
     * Sets the question set for this statistic. Most statistics ignore this,
     * but some question set dependent statistics can use this.
     */
    public void setQuestionSet(QuestionSetModel questionset) {
    }

}
