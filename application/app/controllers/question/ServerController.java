package controllers.question;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.question.serverList;
import models.question.Server;

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
    public static Result list(){
        return ok(
            serverList.render(Server.page(0, 10, "name", "asc", ""), "name", "asc", "")
        );
    }

    /**
     * This result will redirect to the create a server page
     *
     * @return create a server page
     */
    public static Result create(){
        return TODO;
    }

}
