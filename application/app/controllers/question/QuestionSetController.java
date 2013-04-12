package controllers.question;

import com.avaje.ebean.annotation.Transactional;
import controllers.EController;
import models.EMessages;
import models.data.Link;
import models.dbentities.QuestionSetModel;
import models.dbentities.QuestionSetQuestion;
import models.management.ModelState;
import models.question.questionset.QuestionSetManager;
import models.question.questionset.QuestionSetQuestionManager;
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

    // TODO authentication !

    /**
     * Returns the default breadcrumbs for the question set pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        return breadcrumbs;
    }

    /**
     * Returns the "create-question-set" page.
     *
     * @param contestid competition linked with this question set
     * @return create question set page
     */
    public static Result create(String contestid){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questionset.create.breadcrumb"), "/questionset/new"));
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        return ok(views.html.question.questionset.create.render(form, contestid, breadcrumbs, false));
    }

    /**
     * Saves the newly created question set.
     * Redirects to the question set overview page.
     *
     * @param contestid contest linked with this question set
     * @return question set overview page
     */
    public static Result save(String contestid){
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.create.breadcrumb"), "/questionset/new"));
            return badRequest(views.html.question.questionset.create.render(form, contestid, breadcrumbs, true));
        }
        QuestionSetModel questionSetModel = form.get();
        String questionSetId = UUID.randomUUID().toString();
        questionSetModel.id = questionSetId;
        // TODO check of deze question set actief gezet mag worden !
        // TODO opvangen dat wanneer er op Cancel wordt gedrukt, de contest terug verwijderd wordt uit de database!
        questionSetModel.contid = contestid;
        questionSetModel.save();
        return redirect(controllers.question.routes.QuestionSetController.list(questionSetId, 0, "", "", ""));
    }

    /**
     * Returns the overview page for a question set
     *
     * @param questionSetId id of the question set
     * @return overview page for a question set
     */
    public static Result list(String questionSetId, int page, String orderBy, String order, String filter){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
        QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.READ, questionSetId);
        qsqm.setOrder(order);
        qsqm.setOrderBy(orderBy);
        qsqm.setFilter(filter);
        QuestionSetManager questionSetManager = new QuestionSetManager(ModelState.UPDATE, "", questionSetId);
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest().fill(questionSetManager.getFinder().byId(questionSetId));
        return ok(
                views.html.question.questionset.overview.render(breadcrumbs, questionSetId,
                        qsqm.page(page), qsqm, orderBy, order, filter, form, questionSetManager
                ));
    }

    /**
     * Returns the page where a user can add a question to the question set
     *
     * @param questionSetId question set id
     * @return add question to set page
     */
    public static Result addQuestion(String questionSetId){
        Form<QuestionSetQuestion> form = form(QuestionSetQuestion.class).bindFromRequest();
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
        breadcrumbs.add(new Link(EMessages.get("question.questionset.addquestion.brcr"), "/questionset/questions/add"));
        return ok(views.html.question.questionset.addQuestion.render(form, questionSetId, breadcrumbs));
    }

    /**
     * Saves the added question in the database.
     * Redirects to the overview page.
     *
     * @param questionSetId question set id
     * @return overview page
     */
    public static Result updateQuestions(String questionSetId){
        Form<QuestionSetQuestion> form = form(QuestionSetQuestion.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            breadcrumbs.add(new Link(EMessages.get("question.questionset.addquestion.brcr"), "/questionset/questions/add"));
            return badRequest(views.html.question.questionset.addQuestion.render(form, questionSetId, breadcrumbs));
        }
        QuestionSetQuestion questionSetQuestion = form.get();
        questionSetQuestion.qsid = questionSetId;
        // TODO check of de vraag met de opgegeven vraag id wel bestaat !
        questionSetQuestion.save();
        return redirect(routes.QuestionSetController.list(questionSetId, 0, "", "", ""));
    }

    public static Result update(String questionSetId){
        QuestionSetManager questionSetManager = new QuestionSetManager(ModelState.UPDATE, "", questionSetId);
        Form<QuestionSetModel> form = form(QuestionSetModel.class).fill(questionSetManager.getFinder().byId(questionSetId)).bindFromRequest();
        if (form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.READ, questionSetId);
            qsqm.setOrder("asc");
            qsqm.setOrderBy("qid");
            qsqm.setFilter("");
            return badRequest(views.html.question.questionset.overview.render(
                breadcrumbs, questionSetId, qsqm.page(0), qsqm, "qid", "asc", "", form, questionSetManager
            ));
        }
        form.get().update();
        return redirect(routes.QuestionSetController.list(questionSetId, 0, "qid", "asc", ""));
    }

}
