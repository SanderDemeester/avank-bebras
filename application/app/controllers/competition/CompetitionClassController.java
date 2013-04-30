package controllers.competition;

import controllers.EController;
import models.EMessages;
import models.competition.CompetitionClassManager;
import models.data.Link;
import models.management.ModelState;
import models.user.AuthenticationManager;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for crud operations on contest classes.
 *
 * @author Kevin Stobbelaar.
 */
public class CompetitionClassController extends EController {

    /**
     * Returns the default breadcrumbs for the contest pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(String contestid){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competition.breadcrumb"), "/contests"));
        breadcrumbs.add(new Link(EMessages.get("competition.class.breadcrumb"), "/contests/" +  contestid + "/classes"));
        return breadcrumbs;
    }

    private static CompetitionClassManager getManager(String contestid){
        String teacherid = AuthenticationManager.getInstance().getUser().getID();
        CompetitionClassManager manager = new CompetitionClassManager(ModelState.READ, "name", contestid, teacherid);
        manager.setFilter("");
        manager.setOrder("asc");
        manager.setOrderBy("name");
        return manager;
    }

    /**
     * Returns the page with the list of classes for the contest with id = contest id.
     * @param contestid contest id
     * @param page page number
     * @param orderBy order by
     * @param order order
     * @param filter filter
     * @return page with the list of classes
     */
    public static Result list(String contestid, int page, String orderBy, String order, String filter){
        CompetitionClassManager competitionClassManager = getManager(contestid);
        return ok(views.html.competition.classes.render(
                defaultBreadcrumbs(contestid),
                competitionClassManager.page(page),
                competitionClassManager,
                orderBy,
                order,
                filter));
    }

}
