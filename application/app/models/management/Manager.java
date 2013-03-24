package models.management;

import play.mvc.Call;

/**
 * Manager interface for every model that needs visual CRUD operations.
 * An entity must implement this interface in order to use the view list.scala.html
 *
 * @author Kevin Stobbelaar
 *
 */
public interface Manager {

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public String[] getFieldValues();

    /**
     * Returns the names of the fields that have to be represented in a table.
     * These will be the table headers.
     *
     * @return array with the names of fields to be represented in the table
     */
    public String[] getFieldNames();

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page current page number
     * @param pageSize number of items on the page
     * @param orderBy name of the column to sort on
     * @param order ASC or DESC
     * @param filter filter on the items
     * @return Call Route that must be followed
     */
    public Call getListRoute(int page, int pageSize, String orderBy, String order, String filter);

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    public Call getEditRoute();

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    public Call getRemoveRoute();

}
