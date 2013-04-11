package controllers.question;

import models.EMessages;
import models.management.Manager;
import models.management.ModelState;
import models.question.Server;
import play.mvc.Call;
import controllers.question.routes;

/**
 * Manager for the Server entity.
 *
 * @author Kevin Stobbelaar
 * @author Ruben Taelman
 */
public class ServerManager extends Manager<Server> {
    private String id;

    /**
     * Create a new ServerManager based
     * @param id the id for the requested server, only used when editing a question
     * @param state the state the manager should be in
     */
    public ServerManager(String id, ModelState state) {
        this(state);
        this.id = id;
    }

    /**
     * Create a new ServerManager based
     * @param state the state the manager should be in
     */
    public ServerManager(ModelState state){
        super(Server.class, state, "id", "id");
    }

    @Override
    public String[] getColumnHeaders() {
        String[] columnHeaders = {EMessages.get("servers.form.id"), EMessages.get("servers.form.path")};
        return columnHeaders;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page     current page number
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
     * @return Call path of the route that must be followed
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
        return routes.ServerController.update(id);
    }

}
