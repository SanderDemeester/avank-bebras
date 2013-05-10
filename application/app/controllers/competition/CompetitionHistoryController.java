package controllers.competition;

import com.avaje.ebean.Page;
import controllers.EController;
import models.EMessages;
import models.competition.CompetitionHistoryManager;
import models.data.Link;
import models.dbentities.CompetitionModel;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.Role;
import play.mvc.Result;
import views.html.commons.noaccess;
import views.html.competition.history;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing contests taken in the past.
 *
 * @author Kevin Stobbelaar.
 */
public class CompetitionHistoryController extends EController {

    /**
     * Check if the current user is authorized for the competition management
     * @return is the user authorized
     */
    private static boolean isAuthorized() {
        return AuthenticationManager.getInstance().getUser().hasRole(Role.TAKINGCONTESTS);
    }

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

    /**
     * Returns a new competition manager object
     * @param orderBy order by
     * @param order order
     * @param filter filter
     * @return history competition manager
     */
    private static CompetitionHistoryManager getManager(String orderBy, String order, String filter){
        CompetitionHistoryManager competitionManager = new CompetitionHistoryManager(ModelState.READ, "name", null);
        competitionManager.setOrder(order);
        competitionManager.setOrderBy(orderBy);
        competitionManager.setFilter(filter);
        return competitionManager;
    }

    public static Result list(int page, String orderBy, String order, String filter){
        if (!isAuthorized()) return ok(noaccess.render(defaultBreadcrumbs()));;
        CompetitionHistoryManager competitionManager = getManager(orderBy, order, filter);
        Page<CompetitionModel> managerPage = competitionManager.page(page);
        return ok(history.render(defaultBreadcrumbs(), managerPage, competitionManager, order, orderBy, filter));
    }

}
