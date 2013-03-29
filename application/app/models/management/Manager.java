package models.management;

import com.avaje.ebean.Page;
import play.db.ebean.Model.Finder;
import play.mvc.Call;

/**
 * Abstract class for every manager that contains the functionality for CRUD operations
 * on an entity.
 *
 * @author Kevin Stobbelaar
 *
 */
public abstract class Manager<T extends Manageable> {

    private Finder<String, T> finder;
    private int pageSize;

    /**
     * Constructor for manager.
     *
     * @param finder finder object that helps building queries and returning pages.
     * @param pageSize number of elements displayed on one page
     */
    public Manager(Finder<String, T> finder, int pageSize){
        this.finder = finder;
        this.pageSize = pageSize;
    }

    /**
     * Returns a page with elements of type T.
     *
     * @param page     page number
     * @param orderBy  attribute to sort on
     * @param order    sort order
     * @param filter   filter to select specific elements
     * @return the requested page
     */
    public Page<Manageable> page(int page, String orderBy, String order, String filter) {
        return (Page<Manageable>) finder.where()
            .ilike("name", "%" + filter + "%")
            .orderBy(orderBy + " " + order)
                // .fetch("path")
            .findPagingList(pageSize)
            .getPage(page);
    }

    public Finder<String, T> getFinder(){
        return finder;
    }

    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    public abstract String[] getColumnHeaders();

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page     current page number
     * @param orderBy  name of the column to sort on
     * @param order    ASC or DESC
     * @param filter   filter on the items
     * @return Call Route that must be followed
     */
    public abstract Call getListRoute(int page, String orderBy, String order, String filter);

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    public abstract Call getAddRoute();

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    public abstract Call getEditRoute(String id);

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    public abstract Call getRemoveRoute(String id);


}
