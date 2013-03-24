package controllers.question;

import java.util.ArrayList;

import com.avaje.ebean.Page;
import models.data.Link;
import models.management.Manager;
import models.question.Server;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.question.server.newServerForm;
import views.html.question.server.editServerForm;
import views.html.management.list;

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
    public static Result list(int page, int pageSize, String orderBy, String order, String filter){
        Page<Server> serverPage = Server.page(page, pageSize, orderBy, order, filter);
        ArrayList<Manager> items = new ArrayList<Manager>();
        for (Server server : serverPage.getList()){
            items.add(server);
        }
        Manager dummy = new Server();
        return ok(
            list.render(serverPage, items, orderBy, order, filter, dummy, routes.ServerController.create(), new ArrayList<Link>())
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
        return redirect(routes.ServerController.list(0, 10, "name", "asc", ""));
    }

    /**
     * This result will rediect to the edit a server page containing the
     * corresponding form.
     *
     * @param name name of the server to be changed
     * @return edit a server page
     */
    public static Result edit(String name){
        Form<Server> form = form(Server.class).bindFromRequest().fill(Server.finder.ref(name));
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
        Form<Server> form = form(Server.class).fill(Server.finder.byId(name)).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(editServerForm.render(form, name, new ArrayList<Link>()));
        }
        form.get().update();
        // TODO place message in flash for server edited warning in view
        return redirect(routes.ServerController.list(0, 10, "name", "asc", ""));
    }

    /**
     * This will handle the removing of an existing server and redirect
     * to the server list
     *
     * @param name name of the server to be removed
     * @return server list page
     */
    public static Result remove(String name){
        if (Server.finder.byId(name) != null){
            System.out.println("niet null");
        }
        else {
            System.out.println("wel null ezifzeijfoij " + name);
        }
        Server.finder.byId(name).delete();
        return redirect(routes.ServerController.list(0, 10, "name", "asc", ""));
    }

}
