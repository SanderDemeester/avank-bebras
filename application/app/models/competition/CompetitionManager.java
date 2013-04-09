package models.competition;

import com.avaje.ebean.Page;
import controllers.competition.routes;
import models.dbentities.CompetitionModel;
import models.management.ManageableModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

/**
 * Manager for the competition entity.
 *
 * @author Kevin Stobbelaar
 */
public class CompetitionManager extends Manager {

    // TODO: rekening houden met authentication !

    /**
     * Constructor for manager class.
     *
     * @param state      model state
     * @param orderBy    column to be ordered on
     */
    public CompetitionManager(ModelState state, String orderBy) {
        super(CompetitionModel.class, state, orderBy, "name");
        setPageSize(5);
    }

    /**
     * Returns a page with elements of type T.
     *
     * WARNING: it's better to override this method in your own manager!
     *
     * @param page     page number
     * @return the requested page
     */
    @SuppressWarnings("unchecked")
    public Page<ManageableModel> page(int page) {
        return (Page<ManageableModel>) getFinder()
                .where()
                .ilike(filterBy, "%" + filter + "%")
                .orderBy(orderBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }

    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    @Override
    public String[] getColumnHeaders() {
        String[] columnHeaders = {"name", "type", "active", "starttime", "endtime"};
        return columnHeaders;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page   current page number
     * @param filter filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String filter) {
        return routes.CompetitionController.index(page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getAddRoute() {
        return null;
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        return null;
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        return null;
    }

    /**
     * Returns the path of the route that must be followed to save the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getSaveRoute() {
        return null;
    }

    /**
     * Returns the path of the route that must be followed to update(save) the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getUpdateRoute() {
        return null;
    }

    /**
     * Returns the prefix for translation messages.
     *
     * @return name
     */
    @Override
    public String getMessagesPrefix() {
        return "competition.manager";
    }
}
