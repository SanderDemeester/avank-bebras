package models.management;

import com.avaje.ebean.Page;

/**
 * Manager interface for every model that needs visual CRUD operations.
 *
 * @author Kevin Stobbelaar
 *
 */
public interface Manager<T> {

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
    public Page<T> page(int page, int pageSize, String orderBy, String order, String filter);

}
