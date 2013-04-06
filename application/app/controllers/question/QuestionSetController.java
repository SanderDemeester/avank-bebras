package controllers.question;

import controllers.EController;
import models.EMessages;
import models.data.Link;
import models.dbentities.QuestionSetModel;
import play.data.Form;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller for question sets.
 *
 * @auhtor Kevin Stobbelaar.
 */
public class QuestionSetController extends EController {

    /**
     * Returns the "create-question-set" page.
     *
     * @param contestid competition linked with this question set
     * @return create question set page
     */
    public static Result create(String contestid){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("question.questionset.create.breadcrumb"), "/questionset/new"));
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        return ok(views.html.question.questionset.create.render(form, contestid, breadcrumbs));
    }

    public static Result save(String contestid){
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = new ArrayList<Link>();
            breadcrumbs.add(new Link("Home", "/"));
            breadcrumbs.add(new Link(EMessages.get("question.questionset.create.breadcrumb"), "/questionset/new"));
            return badRequest(views.html.question.questionset.create.render(form, contestid, breadcrumbs));
        }
        QuestionSetModel questionSetModel = form.get();
        questionSetModel.id = UUID.randomUUID().toString();
        // TODO check of deze question set actief gezet mag worden !
        questionSetModel.contid = contestid;
        questionSetModel.save();
        return redirect(controllers.competition.routes.CompetitionController.index());
    }

}
