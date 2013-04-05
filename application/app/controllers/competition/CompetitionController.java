package controllers.competition;

import controllers.EController;
import models.EMessages;
import models.data.Link;
import models.dbentities.CompetitionModel;
import play.data.Form;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller for competitions.
 *
 * @author Kevin Stobbelaar
 */
public class CompetitionController extends EController {

    // TODO rekening houden met authentication !

    /**
     * Returns the index page for contests.
     *
     * @return index page
     */
    public static Result index(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competition.name"), "/contests"));
        return ok(views.html.competition.index.render(breadcrumbs));
    }

    /**
     * Returns the create page for contests.
     *
     * @return create contest page
     */
    public static Result create(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competition.name"), "/contests"));
        breadcrumbs.add(new Link(EMessages.get("competition.create.breadcrumb"), "/contests/create"));
        Form<CompetitionModel> form = form(CompetitionModel.class).bindFromRequest();
        return ok(views.html.competition.create.render(form, breadcrumbs));
    }

    /**
     * Saves the newly created contest.
     * Returns the index page for contests.
     *
     * @return index page
     */
    public static Result save(){
        return TODO;
    }

}
