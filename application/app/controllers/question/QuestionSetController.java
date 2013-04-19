package controllers.question;

import controllers.EController;
import models.EMessages;
import models.data.Link;
import models.dbentities.QuestionSetModel;
import models.dbentities.QuestionSetQuestion;
import models.management.ModelState;
import models.question.questionset.QuestionSetManager;
import models.question.questionset.QuestionSetQuestionManager;
import models.user.AuthenticationManager;
import models.user.Role;
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
     * Check if the current user is authorized for this editor
     * @return is the user authorized
     */
    private static boolean isAuthorized() {
        return AuthenticationManager.getInstance().getUser().hasRole(Role.MANAGECONTESTS);
    }

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
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
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
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
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
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
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
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
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
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
        Form<QuestionSetQuestion> form = form(QuestionSetQuestion.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            breadcrumbs.add(new Link(EMessages.get("question.questionset.addquestion.brcr"), "/questionset/questions/add"));
            return badRequest(views.html.question.questionset.addQuestion.render(form, questionSetId, breadcrumbs));
        }
        QuestionSetQuestion questionSetQuestion = form.get();
        questionSetQuestion.qsid = questionSetId;
        int questionId = questionSetQuestion.qid;
        QuestionManager questionManager = new QuestionManager(ModelState.READ);
        if (questionManager.getFinder().byId("" + questionId) == null){
            // question does not exist
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            breadcrumbs.add(new Link(EMessages.get("question.questionset.addquestion.brcr"), "/questionset/questions/add"));
            flash("error", "The question with id = " + questionId + " does not exist.");
            return badRequest(views.html.question.questionset.addQuestion.render(form, questionSetId, breadcrumbs));
        }
        QuestionSetQuestionManager questionSetQuestionManager = new QuestionSetQuestionManager(ModelState.CREATE, questionSetId);
        if (!questionSetQuestionManager.getFinder().where().ieq("qsid", questionSetId).eq("qid", questionId).findList().isEmpty()){
            // question already in this question set
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            breadcrumbs.add(new Link(EMessages.get("question.questionset.addquestion.brcr"), "/questionset/questions/add"));
            flash("error", "The question with id = " + questionId + " is already in this set.");
            return badRequest(views.html.question.questionset.addQuestion.render(form, questionSetId, breadcrumbs));
        }
        questionSetQuestion.save();
        return redirect(routes.QuestionSetController.list(questionSetId, 0, "", "", ""));
    }

    /**
     * Updates the edited question set in the database.
     * Redirects to the question set overview page.
     *
     * @param questionSetId question set id
     * @return redirect to question set overview page.
     */
    public static Result update(String questionSetId){
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
        QuestionSetManager questionSetManager = new QuestionSetManager(ModelState.UPDATE, "level", questionSetId);
        Form<QuestionSetModel> form = form(QuestionSetModel.class).fill(questionSetManager.getFinder().byId(questionSetId)).bindFromRequest();
        if (form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.READ, questionSetId);
            qsqm.setOrder("asc");
            qsqm.setOrderBy("qid");
            qsqm.setFilter("");
            flash("questionset-error", EMessages.get("forms.error"));
            return badRequest(views.html.question.questionset.overview.render(
                breadcrumbs, questionSetId, qsqm.page(0), qsqm, "qid", "asc", "", form, questionSetManager
            ));
        }
        QuestionSetModel questionSetModel = form.get();
        questionSetModel.id = questionSetId;
        questionSetModel.update();
        return redirect(routes.QuestionSetController.list(questionSetId, 0, "qid", "asc", ""));
    }

    /**
     * Removes a question for this question set.
     * Redirects to the question set overview page.
     *
     * @param questionSetId question set id
     * @param questionId question id
     * @return redirect to question set overview page
     */
    public static Result removeQuestion(String questionSetId, String questionId){
        if (!isAuthorized()) return redirect(controllers.routes.Application.index());
        QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.DELETE, questionSetId);
        List<QuestionSetQuestion> questions = qsqm.getFinder().where().ieq("qsid", questionSetId).eq("qid", new Integer(questionId)).findList();
        if (!questions.isEmpty()){
            questions.get(0).delete();
        }
        return redirect(routes.QuestionSetController.list(questionSetId, 0, "qid", "asc", ""));
    }

}