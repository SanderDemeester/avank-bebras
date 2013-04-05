package controllers.question.questionset;

import controllers.EController;
import models.data.Link;
import models.dbentities.QuestionSetModel;
import models.question.questionset.QuestionSetManager;
import play.data.Form;
import play.mvc.Result;
import views.html.question.questionsets.questionSetManagement;
import views.html.question.questionsets.createQuestionSetForm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Question set controller.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetController extends EController {

    private static QuestionSetManager qsm = new QuestionSetManager();

    /**
     * Returns the overview page for question sets.
     *
     * @param selectedqsid id of the selected question set
     * @param page page number for question sets
     * @param orderBy column to order the question sets
     * @param order ASC or DESC
     * @param filter filter for question sets
     * @return overview page
     */
    public static Result overview(String selectedqsid, int page, String orderBy, String order, String filter){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question sets", "/questionsets"));
        return ok(questionSetManagement.render(selectedqsid, breadcrumbs, qsm.page(page, orderBy, order, filter), qsm, orderBy, order, filter, null));
    }

    /**
     * Returns a page with a form to create a new question set.
     *
     * @return new question set page
     */
    public static Result create(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question sets", "/questionsets"));
        breadcrumbs.add(new Link("Question sets", "/questionsets/create"));
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        return ok(createQuestionSetForm.render(form, breadcrumbs));
    }

    /**
     * Saves the new question set in the database.
     * Returns the overview page.
     *
     * @return overview page
     */
    public static Result save(){
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest();
        if(form.hasErrors()) {
            List<Link> breadcrumbs = new ArrayList<Link>();
            breadcrumbs.add(new Link("Home", "/"));
            breadcrumbs.add(new Link("Question sets", "/questionsets"));
            breadcrumbs.add(new Link("Question sets", "/questionsets/create"));
            return badRequest(createQuestionSetForm.render(form, breadcrumbs));
        }
        QuestionSetModel questionSet = form.get();
        questionSet.id = UUID.randomUUID().toString(); // create unique UUID
        // active box got checked
        if("true".equals(form.field("active").value())) {
            // TODO check if this question can be active at the moment !
            questionSet.active = true;
        }
        questionSet.save();
        // TODO place message in flash for "server add warning" in view
        return redirect(routes.QuestionSetController.overview("",0,"","",""));
    }

    /**
     * Returns the overview page with an edit form for the selected question set.
     *
     * @param id id of the selected question set
     * @param page page number for question sets
     * @param orderBy column to order the question sets
     * @param order ASC or DESC
     * @param filter filter for question sets
     * @return overview page
     */
    public static Result edit(String id, int page, String orderBy, String order, String filter){
        String qsName = qsm.getFinder().byId(id).getName();
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question sets", "/questionsets"));
        breadcrumbs.add(new Link("Question set: " + qsName, "/questionsets/:" + qsName));
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest().fill(new QuestionSetManager().getFinder().ref(id));
        return ok(questionSetManagement.render(id, breadcrumbs, qsm.page(page, orderBy, order, filter), qsm, orderBy, order, filter, form));
    }

    /**
     * Updates the edited object in the database.
     * Returns the overview page.
     *
     * @param id id of the question set that has been edited
     * @return overview page
     */
    public static Result update(String id){
        Form<QuestionSetModel> form = form(QuestionSetModel.class).fill(qsm.getFinder().byId(id)).bindFromRequest();
        if(form.hasErrors()) {
            String qsName = qsm.getFinder().byId(id).getName();
            List<Link> breadcrumbs = new ArrayList<Link>();
            breadcrumbs.add(new Link("Home", "/"));
            breadcrumbs.add(new Link("Question sets", "/questionsets"));
            breadcrumbs.add(new Link("Question set: " + qsName, "/questionsets/:" + qsName));
            return badRequest(questionSetManagement.render(id, breadcrumbs, qsm.page(0, "", "", ""), qsm, "", "", "", form));
        }
        QuestionSetModel questionSet = form.get();
        // active box got checked
        if("true".equals(form.field("active").value())) {
            // TODO check if this question can be active at the moment !
            questionSet.active = true;
        }
        questionSet.update();
        // TODO place message in flash for server edited warning in view
        return redirect(routes.QuestionSetController.overview("", 0, "", "", ""));
    }

    /**
     * Removes the selected object from the database.
     * Returns the overview page.
     *
     * @param id id of the removed object
     * @return overview page
     */
    // TODO question set wordt pas verwijderd na refreshen van de pagina :/
    public static Result remove(String id){
        QuestionSetModel questionSet = qsm.getFinder().byId(id);
        questionSet.delete();
        return redirect(controllers.question.questionset.routes.QuestionSetController.overview("", 0, "", "", ""));
    }


}
