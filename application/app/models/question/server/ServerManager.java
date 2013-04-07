package models.question.server;

import models.management.Manager;
import play.mvc.Call;
import controllers.question.server.routes;

/**
 * Manager for the Server entity.
 *
 * @auhtor Kevin Stobbelaar
 */
public class ServerManager extends Manager<Server> {

    public ServerManager(){
        super(Server.class);
        this.setOrderBy("name");
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
    public Call getListRoute(int page, String filter) {
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
    
    @Override
    public play.api.mvc.Call getSaveRoute() {
        return routes.ServerController.save();
    }
    
    /**
     * Returns the name of the object.
     *
     * @return name
     */
    @Override
    public String getMessagesPrefix() {
        return "servers";
    }

    @Override
    public play.api.mvc.Call getUpdateRoute() {
        // TODO Auto-generated method stub
        return routes.ServerController.update("TODO");
    }

}
