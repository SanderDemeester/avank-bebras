package controllers.competition;

import controllers.EController;
import models.data.Link;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller for competitions.
 *
 * @author Kevin Stobbelaar
 */
public class CompetitionController extends EController {

    /**
     * Returns the index page for contests.
     *
     * @return index page
     */
    public static Result index(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Contests", "/contests"));
        return ok(views.html.competition.index.render(breadcrumbs));
    }

}
