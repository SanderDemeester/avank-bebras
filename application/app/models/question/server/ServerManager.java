package models.question.server;

import controllers.question.server.routes;
import models.management.Manager;
import play.db.ebean.Model.Finder;
import play.mvc.Call;

/**
 * Manager for the Server entity.
 *
 * @auhtor Kevin Stobbelaar
 */
public class ServerManager extends Manager<Server> {

    public ServerManager(){
        super(new Finder<String, models.question.server.Server>(String.class, models.question.server.Server.class), 6);
    }

    @Override
    public String[] getColumnHeaders() {
        String[] columnHeaders = {"name", "path"};
        return columnHeaders;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page     current page number
     * @param orderBy  name of the column to sort on
     * @param order    ASC or DESC
     * @param filter   filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return routes.ServerController.list(page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getAddRoute(){
        return routes.ServerController.create();
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        return routes.ServerController.edit(id);
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        return routes.ServerController.remove(id);
    }

}
