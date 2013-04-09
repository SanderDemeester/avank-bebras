package controllers.competition;

import controllers.EController;
import controllers.question.QuestionSetController;
import models.EMessages;
import models.competition.CompetitionManager;
import models.data.Link;
import models.dbentities.CompetitionModel;
import models.management.ModelState;
import models.question.questionset.QuestionSetQuestionManager;
import play.data.Form;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Controller for competitions.
 *
 * @author Kevin Stobbelaar
 */
public class CompetitionController extends EController {

    // TODO rekening houden met authentication !

    /**
     * Returns the default breadcrumbs for the contest pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competition.name"), "/contests"));
        return breadcrumbs;
    }

    /**
     * Returns the index page for contests.
     * @param page page
     * @param orderBy column to order on
     * @param order sort order
     * @param filter string to filter on
     * @return index page
     */
    @SuppressWarnings("unchecked")
    public static Result index(int page, String orderBy, String order, String filter){
        CompetitionManager competitionManager = new CompetitionManager(ModelState.READ, orderBy);
        competitionManager.setOrder(order);
        competitionManager.setOrderBy(orderBy);
        competitionManager.setFilter(filter);
        return ok(views.html.competition.index.render(defaultBreadcrumbs(), competitionManager.page(page), competitionManager
                  , orderBy, order, filter));
    }

    /**
     * Returns the create page for contests.
     *
     * @return create contest page
     */
    public static Result create(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("competition.create.breadcrumb"), "/contests/create"));
        Form<CompetitionModel> form = form(CompetitionModel.class).bindFromRequest();
        return ok(views.html.competition.create.render(form, breadcrumbs, false));
    }

    /**
     * Saves the newly created contest.
     * Returns the page with step 2: create question set.
     *
     * @return create question set page
     */
    public static Result save(){
        Form<CompetitionModel> form = form(CompetitionModel.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("competition.create.breadcrumb"), "/contests/create"));
            return badRequest(views.html.competition.create.render(form, breadcrumbs, true));
        }
        CompetitionModel competitionModel = form.get();
        competitionModel.id = UUID.randomUUID().toString();
        // TODO check startdate < enddate
        // TODO datums zijn voorlopig nog zonder tijdstip !
        competitionModel.save();
        return redirect(controllers.question.routes.QuestionSetController.create(competitionModel.id));
    }

}
