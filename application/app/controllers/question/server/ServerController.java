package controllers.question.server;

import java.util.ArrayList;

import controllers.question.server.routes;
import models.data.Link;
import models.question.server.Server;
import models.question.server.ServerManager;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import views.html.question.server.newServerForm;
import views.html.question.server.editServerForm;
import views.html.question.server.serverManagement;

/**
 * ServerController controller.
 *
 * @author Kevin Stobbelaar
 */
public class ServerController extends Controller {

    /**
     * This result will redirect to the server list page
     *
     * @return server list page
     */
    public static Result list(int page, String orderBy, String order, String filter){
        ServerManager serverManager = new ServerManager();
        return ok(
            serverManagement.render(serverManager.page(page, orderBy, order, filter), serverManager, orderBy, order, filter, new ArrayList<Link>())
        );
    }

    /**
     * This result will redirect to the create a server page
     * containing the corresponding form.
     *
     * @return create a server page
     */
    public static Result create(){
        Form<Server> form = form(Server.class).bindFromRequest();
        return ok(newServerForm.render(form, new ArrayList<Link>()));
    }

    /**
     * This will handle the creation of a new server and redirect
     * to the server list
     *
     * @return server list page
     */
    public static Result save(){
        Form<Server> form = form(Server.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(newServerForm.render(form, new ArrayList<Link>()));
        }
        form.get().save();
        // TODO place message in flash for "server add warning" in view
        return Results.redirect(routes.ServerController.list(0, "name", "asc", ""));
    }

    /**
     * This result will rediect to the edit a server page containing the
     * corresponding form.
     *
     * @param name name of the server to be changed
     * @return edit a server page
     */
    public static Result edit(String name){
        Form<Server> form = form(Server.class).bindFromRequest().fill(new ServerManager().getFinder().ref(name));
        return ok(editServerForm.render(form, name, new ArrayList<Link>()));
    }

    /**
     * This will handle the update of an existing server and redirect
     * to the server list
     *
     * @param name name of the server to be updated
     * @return server list page
     */
    public static Result update(String name){
        Form<Server> form = form(Server.class).fill(new ServerManager().getFinder().byId(name)).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(editServerForm.render(form, name, new ArrayList<Link>()));
        }
        form.get().update();
        // TODO place message in flash for server edited warning in view
        return redirect(routes.ServerController.list(0, "name", "asc", ""));
    }

    /**
     * This will handle the removing of an existing server and redirect
     * to the server list
     *
     * @param name name of the server to be removed
     * @return server list page
     */
    public static Result remove(String name){
        Server server = new ServerManager().getFinder().byId(name);
        server.delete();
        return redirect(routes.ServerController.list(0, "name", "asc", ""));
    }

}
