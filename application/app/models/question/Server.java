package models.question;

import com.avaje.ebean.Page;
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
public class Server extends Model {

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
}
