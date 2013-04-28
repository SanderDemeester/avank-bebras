package controllers.competition;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import controllers.EController;
import models.EMessages;
import models.competition.Competition;
import models.competition.CompetitionManager;
import models.competition.CompetitionType;
import models.competition.TakeCompetitionManager;
import models.data.Grade;
import models.data.Link;
import models.dbentities.CompetitionModel;
import models.dbentities.QuestionSetModel;
import models.management.ModelState;
import models.question.QuestionSet;
import models.user.AuthenticationManager;
import models.user.Role;
import models.user.UserType;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for taking competitions. Includes the listing of available
 * competitions for each user type.
 *
 * @author Kevin Stobbelaar.
 */
public class TakeCompetitionController extends EController {

    /**
     * Checks if the current user has the given role.
     * @return true is the user has the given role
     */
    private static boolean userType(UserType type) {
        return AuthenticationManager.getInstance().getUser().getType().equals(type);
    }

    /**
     * Returns the default breadcrumbs for the contest pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competitions.breadcrumb"), "/available-contests"));
        return breadcrumbs;
    }

    /**
     * Returns a new competition manager object.
     * @return competition manager
     */
    private static TakeCompetitionManager getManager(){
        TakeCompetitionManager competitionManager = new TakeCompetitionManager(ModelState.READ, "name", null);
        competitionManager.setOrder("asc");
        competitionManager.setOrderBy("name");
        competitionManager.setFilter("");
        return competitionManager;
    }

    /**
     * Returns a page with a listing of available competitions
     * for the current type of user.
     * @return  available competitions list page
     */
    public static Result list(int page, String orderBy, String order, String filter){
        if (userType(UserType.ANON)){
            // anonymous user
            // TODO alleen "running" competities mogen in de lijst verschijnen !
            TakeCompetitionManager competitionManager = getManager();
            competitionManager.setExpressionList(competitionManager.getFinder().where().eq("type", CompetitionType.ANONYMOUS));
            Page<CompetitionModel> managerPage = competitionManager.page(page);
            return ok(views.html.competition.contests.render(defaultBreadcrumbs(), managerPage, competitionManager, order, orderBy, filter));
        }
        return TODO;
    }

    public static Result takeCompetition(String id){
        CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
        Competition competition = new Competition(competitionModel);
        // TODO juiste question set kiezen !
        QuestionSet questionSet = competition.getQuestionSet(competition.getAvailableGrades().get(0));
        return ok(views.html.competition.run.questionSet.render(questionSet, null, defaultBreadcrumbs()));
    }

}
