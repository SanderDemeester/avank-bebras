package models.question;

import com.avaje.ebean.Page;
import models.management.Manager;
import play.Logger;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * ServerController entity managed by Ebean.
 *
 * @author Ruben Taelman
 * @author Kevin Stobbelaar
 *
 */
@Entity
@Table(name="servers")
public class Server extends Model implements Manager {

    @Id
    @Column(name="id")
    public String name;

    @Transient
    @Constraints.Required
    public String baseUrl;

    @Column(name="location")
    @Constraints.Required
    public String path;

    /**
     * Generic query helper for ServerController entity.
     */
    public static Finder<String, Server> finder = new Finder<String, Server>(String.class, Server.class);

    /**
     * Returns a page with elements of type T.
     *
     * @param page     page number
     * @param pageSize elements per page
     * @param orderBy  attribute to sort on
     * @param order    sort order
     * @param filter   filter to select specific elements
     * @return the requested page
     */
    public static Page<Server> page(int page, int pageSize, String orderBy, String order, String filter) {
        return finder.where()
            .ilike("name", "%" + filter + "%")
            .orderBy(orderBy + " " + order)
            // .fetch("path")
            .findPagingList(pageSize)
            .getPage(page);
    }

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {this.name, this.path};
        return result;
    }

    /**
     * Returns the names of the fields that have to be represented in a table.
     * These will be the table headers.
     *
     * @return array with the names of fields to be represented in the table
     */
    @Override
    public String[] getFieldNames() {
        String[] result = {"Server's name", "Servers's path"};
        return result;
    }

    /**
     * Returns the id of the this object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return name;
    }
}
