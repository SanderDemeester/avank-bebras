package models.data;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Constraints;

import models.data.manager.DataElement;

/**
 * This clas represents a Link, as the combination of a name and a URL.
 * @author Felix Van der Jeugt
 * @author Ruben Taelman
 */
@Entity @Table(name="Links")
public class Link implements DataElement {

    /** The name of the Link, as it appears to the user. */
    @Id public String title;
    /**
     * The URL the link points to. Can be anything accepted in href attributes.
     */
    @Constraints.Required public String url;

    /**
     * Creates a new link. This method links the given name to a URL created
     * from the given string.
     * @param title The name of the link.
     * @param url The url as a String.
     */
    public Link(String title, String url) {
        this.title = title;
        this.url  = url;
    }

    @Override public String[] strings() {
        return new String[]{ title, url };
    }

    @Override public String id() {
        return title;
    }

    /**
     * Returns the name of this link.
     * @return The name of the link.
     */
    public String getName() {
        return title;
    }

    /**
     * Return the URL this link points to.
     * @return The URL.
     */
    public String getUrl() {
        return url;
    }

}
