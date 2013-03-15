package models.question;

import com.avaje.ebean.Page;
import models.management.Manager;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * ServerController entity managed by Ebean.
 *
 * @author Ruben Taelman & Kevin Stobbelaar
 *
 */
@Entity
// @Table(name="ServerController") could be needed, not needed if we use Ebean's DDL generation
public class Server extends Model {

    @Id
	public String name;

    @Transient
    @Constraints.Required
    public String baseUrl;

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
}
