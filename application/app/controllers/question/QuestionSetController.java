package controllers.question;

import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Difficulty;
import models.data.Grade;
import models.data.Link;
import models.dbentities.CompetitionModel;
import models.dbentities.QuestionModel;
import models.dbentities.QuestionSetModel;
import models.dbentities.QuestionSetQuestion;
import models.management.ModelState;
import models.question.questionset.QuestionSetManager;
import models.question.questionset.QuestionSetQuestionManager;
import models.user.AuthenticationManager;
import models.user.Role;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.data.Form;
import play.libs.Json;
import play.mvc.Result;

import com.avaje.ebean.Ebean;

import controllers.EController;
/**
 * Controller for question sets.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetController extends EController {

    /**
     * Check if the current user is authorized for this editor
     * @param role the role the user has to have
     * @return is the user authorized
     */
    private static boolean isAuthorized(Role role) {
        return AuthenticationManager.getInstance().getUser().hasRole(role);
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
        if (!isAuthorized(Role.MANAGECONTESTS)) return redirect(controllers.routes.Application.index());
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
        if (!isAuthorized(Role.MANAGECONTESTS)) return redirect(controllers.routes.Application.index());
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.create.breadcrumb"), "/questionset/new"));
            return badRequest(views.html.question.questionset.create.render(form, contestid, breadcrumbs, true));
        }
        QuestionSetModel questionSetModel = form.get();
        questionSetModel.grade = Ebean.find(Grade.class).where().ieq("name", form.field("gradetext").value()).findUnique();
        QuestionSetModel oldQuestionSetModel = Ebean.find(QuestionSetModel.class)
                .where()
                .eq("grade", questionSetModel.grade)
                .eq("contid", contestid)
                .findUnique();
        if (oldQuestionSetModel != null){
            // there exists a question set with the same grade
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.create.breadcrumb"), "/questionset/new"));
            flash("questionset-error", EMessages.get("question.questionset.doublegrade"));
            return badRequest(views.html.question.questionset.create.render(form, contestid, breadcrumbs, true));
        }
        // TODO check of deze question set actief gezet mag worden !
        // TODO opvangen dat wanneer er op Cancel wordt gedrukt, de contest terug verwijderd wordt uit de database!
        questionSetModel.contest = Ebean.find(CompetitionModel.class).where().ieq("id", contestid).findUnique();
        questionSetModel.save();
        return redirect(controllers.question.routes.QuestionSetController.list(questionSetModel.id, 0, "", "", ""));
    }

    /**
     * Returns the overview page for a question set
     *
     * @param questionSetId id of the question set
     * @param page page nr
     * @param orderBy order field
     * @param order order
     * @param filter filter
     * @return overview page for a question set
     */
    public static Result list(int questionSetId, int page, String orderBy, String order, String filter){
        if (! (isAuthorized(Role.MANAGECONTESTS) && isAuthorized(Role.VIEWCONTESTS)) ) return redirect(controllers.routes.Application.index());
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
        QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.READ, questionSetId);
        qsqm.setOrder(order);
        qsqm.setOrderBy(orderBy);
        qsqm.setFilter(filter);
        QuestionSetManager questionSetManager = new QuestionSetManager(ModelState.UPDATE, "", questionSetId);
        QuestionSetModel questionSetModel = questionSetManager.getFinder().byId(Integer.toString(questionSetId));
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest().fill(questionSetModel);
        return ok(
                views.html.question.questionset.overview.render(breadcrumbs, questionSetId,
                        qsqm.page(page), qsqm, orderBy, order, filter, form, questionSetManager, questionSetModel.grade.getName()
                ));
    }

    /**
     * Returns the page where a user can add a question to the question set
     *
     * @param questionSetId question set id
     * @return add question to set page
     */
    public static Result addQuestion(int questionSetId){
        if (!isAuthorized(Role.MANAGECONTESTS)) return redirect(controllers.routes.Application.index());
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
    public static Result updateQuestions(int questionSetId){
        if (!isAuthorized(Role.MANAGECONTESTS)) return redirect(controllers.routes.Application.index());
        Form<QuestionSetQuestion> form = form(QuestionSetQuestion.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            breadcrumbs.add(new Link(EMessages.get("question.questionset.addquestion.brcr"), "/questionset/questions/add"));
            return badRequest(views.html.question.questionset.addQuestion.render(form, questionSetId, breadcrumbs));
        }
        QuestionSetQuestion questionSetQuestion = form.get();
        questionSetQuestion.questionSet = Ebean.find(QuestionSetModel.class).where().eq("id", questionSetId).findUnique();
        int questionId = questionSetQuestion.qid;
        questionSetQuestion.difficulty = Ebean.find(Difficulty.class).where().ieq("name", form.field("diftext").value()).findUnique();
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
        if (!questionSetQuestionManager.getFinder().where().eq("qsid", questionSetId).eq("qid", questionId).findList().isEmpty()){
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
    public static Result update(int questionSetId){
        if (!isAuthorized(Role.MANAGECONTESTS)) return redirect(controllers.routes.Application.index());
        QuestionSetManager questionSetManager = new QuestionSetManager(ModelState.UPDATE, "grade", questionSetId);
        Form<QuestionSetModel> form = form(QuestionSetModel.class).fill(Ebean.find(QuestionSetModel.class, questionSetId)).bindFromRequest();
        if (form.hasErrors()) {
            List<Link> breadcrumbs = defaultBreadcrumbs();
            breadcrumbs.add(new Link(EMessages.get("question.questionset.overview"), "/questionset/questions"));
            QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.READ, questionSetId);
            qsqm.setOrder("asc");
            qsqm.setOrderBy("qid");
            qsqm.setFilter("");
            flash("questionset-error", EMessages.get("forms.error"));
            return badRequest(views.html.question.questionset.overview.render(
                breadcrumbs, questionSetId, qsqm.page(0), qsqm, "qid", "asc", "", form, questionSetManager, form.get().grade.getName()
            ));
        }
        QuestionSetModel questionSetModel = form.get();
        questionSetModel.grade = Ebean.find(Grade.class).where().ieq("name", form.field("gradetext").value()).findUnique();
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
    public static Result removeQuestion(int questionSetId, String questionId){
        if (!isAuthorized(Role.MANAGECONTESTS)) return redirect(controllers.routes.Application.index());
        QuestionSetQuestionManager qsqm = new QuestionSetQuestionManager(ModelState.DELETE, questionSetId);
        List<QuestionSetQuestion> questions = qsqm.getFinder().where().eq("qsid", questionSetId).eq("qid", new Integer(questionId)).findList();
        if (!questions.isEmpty()){
            questions.get(0).delete();
        }
        return redirect(routes.QuestionSetController.list(questionSetId, 0, "qid", "asc", ""));
    }

    /**
     * Returns the data source for the type ahead input field
     * @return data source
     */
    public static Result typeAhead(){
        ObjectNode objectNode = Json.newObject();
        ArrayNode arrayNode = objectNode.putArray("array");
        List<QuestionModel> questionModels = Ebean.find(QuestionModel.class).findList();
        for (int i = 0; i < questionModels.size(); i++){
            arrayNode.add(questionModels.get(i).getID());
        }
        return ok(objectNode);
    }


}
