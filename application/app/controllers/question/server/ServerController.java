package controllers.question.server;

import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.management.ModelState;
import models.question.server.Server;
import models.question.server.ServerManager;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.Result;
import play.mvc.Results;
import views.html.question.server.editServerForm;
import views.html.question.server.newServerForm;
import views.html.question.server.serverManagement;

import com.avaje.ebean.annotation.Transactional;

import controllers.EController;

/**
 * ServerController controller.
 *
 * @author Kevin Stobbelaar
 */
public class ServerController extends EController {

    private Finder<String,Server> serverFinder = new Finder<String,Server>(String.class, Server.class);

    /**
     * Make default breadcrumbs for this controller
     * @return default breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs() {
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("servermanagement.servers.name"), "/servers"));
        return breadcrumbs;
    }

    /**
     * This result will redirect to the server list page
     *
     * @return server list page
     */
    @Transactional(readOnly=true)
    public static Result list(int page, String orderBy, String order, String filter){
        List<Link> breadcrumbs = defaultBreadcrumbs();

        ServerManager serverManager = new ServerManager(ModelState.READ);
        serverManager.setOrder(order);
        serverManager.setOrderBy(orderBy);
        serverManager.setFilter(filter);

        return ok(
            serverManagement.render(serverManager.page(page), serverManager, orderBy, order, filter, breadcrumbs)
        );
    }

    /**
     * This result will redirect to the create a server page
     * containing the corresponding form.
     *
     * @return create a server page
     */
    @Transactional(readOnly=true)
    public static Result create(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("servermanagement.servers.new"), "/servers/create"));

        Form<Server> form = form(Server.class).bindFromRequest();

        ServerManager manager = new ServerManager(ModelState.CREATE);
        manager.setIgnoreErrors(true);

        return ok(newServerForm.render(form, manager, breadcrumbs));
    }

    /**
     * This will handle the creation of a new server and redirect
     * to the server list
     *
     * @return server list page
     */
    @Transactional
    public static Result save(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("servermanagement.servers.new"), "/servers/create"));

        Form<Server> form = form(Server.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(newServerForm.render(form, new ServerManager(ModelState.CREATE), breadcrumbs));
        }
        form.get().save();
        // TODO place message in flash for "server add warning" in view
        return Results.redirect(routes.ServerController.list(0, "id", "asc", ""));
    }

    /**
     * This result will rediect to the edit a server page containing the
     * corresponding form.
     *
     * @param name name of the server to be changed
     * @return edit a server page
     */
    @Transactional(readOnly=true)
    public static Result edit(String name){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("servermanagement.servers.server") + " " + name, "/servers/:" + name));

        ServerManager manager = new ServerManager(name, ModelState.UPDATE);
        manager.setIgnoreErrors(true);

        Form<Server> form = form(Server.class).bindFromRequest().fill(manager.getFinder().ref(name));
        return ok(editServerForm.render(form, manager, breadcrumbs));
    }

    /**
     * This will handle the update of an existing server and redirect
     * to the server list
     *
     * @param name name of the server to be updated
     * @return server list page
     */
    @Transactional
    public static Result update(String name){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("servermanagement.servers.server") + " " + name, "/servers/:" + name));

        ServerManager manager = new ServerManager(name, ModelState.UPDATE);

        Form<Server> form = form(Server.class).fill(manager.getFinder().byId(name)).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(editServerForm.render(form, manager, breadcrumbs));
        }
        form.get().update();
        // TODO place message in flash for server edited warning in view
        return redirect(routes.ServerController.list(0, "id", "asc", ""));
    }

    /**
     * This will handle the removing of an existing server and redirect
     * to the server list
     *
     * @param name name of the server to be removed
     * @return server list page
     */
    @Transactional
    public static Result remove(String name){
        Server server = new ServerManager(ModelState.DELETE).getFinder().byId(name);
        server.delete();
        return redirect(routes.ServerController.list(0, "id", "asc", ""));
    }

}