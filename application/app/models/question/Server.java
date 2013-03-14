package models.question;

import com.avaje.ebean.Page;
import models.management.Manager;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Server entity managed by Ebean.
 *
 * @author Ruben Taelman & Kevin Stobbelaar
 *
 */
@Entity
public class Server extends Model implements Manager<Server> {

    @Id
	public String name;

    @Constraints.Required
    public String baseUrl;

    @Constraints.Required
	public String path;

    /**
     * Generic query helper for Server entity.
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
    @Override
    public Page<Server> page(int page, int pageSize, String orderBy, String order, String filter) {
        return finder.where()
            .ilike("name", "%" + filter + "%")
            .orderBy(orderBy + " " + order)
            .fetch("path")
            .findPagingList(pageSize)
            .getPage(page);
    }
}
