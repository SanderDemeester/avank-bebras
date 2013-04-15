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
        uniqueConstraints=@UniqueConstraint(columnNames={"ordr"})
)
public class Difficulty implements DataElement {

    @Id public String name;
    public int ordr;

    public Difficulty(String name, int ordr) {
        this.name = name;
        this.ordr = ordr;
    }

    @Override public String[] strings() {
        return new String[] { name, Integer.toString(ordr) };
    }

    @Override public String id() {
        return name;
    }
}
