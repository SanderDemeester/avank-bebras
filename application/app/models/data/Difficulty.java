package models.data;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.data.manager.DataElement;

/**
 * Represents a Difficulty that can be used in various QuestionSets
 * @author Ruben Taelman
 * @author Felix Van der Jeugt
 */
@Entity @Table(
        name="Difficulties",
        uniqueConstraints=@UniqueConstraint(columnNames={"rank"})
)
public class Difficulty implements DataElement {

    /**
     * name
     */
    @Id public String name;
    /**
     * rank
     */
    public int rank;
    /**
     * correct point
     */
    public int cpoints;
    /**
     * wrong points
     */
    public int wpoints;
    /**
     * nothing filled in points
     */
    public int npoints;

    /**
     * Creates a new difficulty level with the given name and rank. The higher
     * the rank, the harder this difficulty level.
     * @param name The name of the level.
     * @param rank The difficulty of this level.
     * @param cpoints The points received when answered correct.
     * @param wpoints The points received when answered incorrect.
     * @param npoints The points received left open.
     */
    public Difficulty(String name, int rank, int cpoints, int wpoints, int npoints) {
        this.name = name;
        this.rank = rank;
        this.cpoints = cpoints;
        this.wpoints = wpoints;
        this.npoints = npoints;
    }

    @Override public String[] strings() {
        return new String[] { name, Integer.toString(rank),
            Integer.toString(cpoints), Integer.toString(wpoints),
            Integer.toString(npoints) };
    }

    @Override public String id() {
        return name;
    }
}
