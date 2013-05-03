package controllers.competition;

import controllers.EController;
import models.EMessages;
import models.data.Link;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing contests taken in the past.
 *
 * @author Kevin Stobbelaar.
 */
public class CompetitionHistoryController extends EController {

    /**
     * Returns the default breadcrumbs for the contest pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competitions.breadcrumb"), "/available-contests"));
        breadcrumbs.add(new Link(EMessages.get("competition.history.breadcrumb"), "/available-contests/history"));
        return breadcrumbs;
    }

    public static Result list(int page, String orderBy, String order, String filter){
        return TODO;
    }

}
