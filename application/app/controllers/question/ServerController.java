package controllers.question;

import models.question.Server;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.question.serverForm;
import views.html.question.serverList;

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
        return ok(
            serverList.render(Server.page(page, pageSize, orderBy, order, filter), orderBy, order, filter)
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
        return ok(serverForm.render(form));
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
            return badRequest(serverForm.render(form));
        }
        form.get().save();
        // TODO place message in flash for "server add warning" in view
        return redirect(routes.ServerController.list(0, 10, "name", "asc", ""));
    }

}
