package models.competition;

import com.avaje.ebean.Page;
import controllers.competition.routes;
import models.dbentities.CompetitionModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for the competition entity.
 *
 * @author Kevin Stobbelaar
 */
public class CompetitionManager extends Manager<CompetitionModel> {

    private String contestid;

    /**
     * Constructor for manager class.
     *
     * @param contestid  contest id
     * @param state      model state
     * @param orderBy    column to be ordered on
     */
    public CompetitionManager(ModelState state, String orderBy, String contestid) {
        super(CompetitionModel.class, state, orderBy, "name");
        this.contestid = contestid;
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
    @Override
    public Page<CompetitionModel> page(int page) {
        return  getFinder()
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
    public List<String> getColumnHeaders() {
        ArrayList<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add("name");
        columnHeaders.add("type");
        columnHeaders.add("active");
        columnHeaders.add("starttime");
        columnHeaders.add("endtime");
        columnHeaders.add("creator");
        columnHeaders.add("duration");
        return columnHeaders;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page   current page number
     * @param orderBy current order by
     * @param order   current order
     * @param filter filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return routes.CompetitionController.index(page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getAddRoute() {
        return routes.CompetitionController.create();
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        return routes.CompetitionController.viewCompetition(id, 0, "", "", "");
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        return routes.CompetitionController.removeCompetition(id);
    }

    /**
     * Returns the path of the route that must be followed to save the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getSaveRoute() {
        return routes.CompetitionController.save();
    }

    /**
     * Returns the path of the route that must be followed to update(save) the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getUpdateRoute() {
        return routes.CompetitionController.updateCompetition(contestid);
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
