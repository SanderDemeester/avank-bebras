package controllers.statistics;

import java.util.List;
import java.util.ArrayList;

import play.mvc.Result;

import controllers.EController;
import models.data.Link;
import views.html.statistics.statistics;

/**
 * This class returns the statistics page for the logged in user.
 * @author Felix Van der Jeugt
 */
public class Statistics extends EController {

    /* Our lovely breadcrumbs. */
    private static List<Link> breadcrumbs = new ArrayList<Link>();
    static {
        breadcrumbs.add(new Link("app.home", "/"));
        breadcrumbs.add(new Link("statistics.title", "/statistics"));
    }

    /** Display the main statistics page. */
    public static Result show() {
        return ok(statistics.render(null, breadcrumbs));
    }

}
