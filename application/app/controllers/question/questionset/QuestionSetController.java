package controllers.question.questionset;

import models.data.Link;
import models.dbentities.QuestionSetModel;
import models.question.questionset.QuestionSetManager;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.question.questionsets.questionSetManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Question set controller.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetController extends Controller {

    private static QuestionSetManager qsm = new QuestionSetManager();

    /**
     * Returns the overview page for question sets.
     *
     * @param selectedqsid id of the selected question set
     * @param page page number for question sets
     * @param orderBy column to order the question sets
     * @param order ASC or DESC
     * @param filter filter for question sets
     * @return overview page.
     */
    public static Result overview(String selectedqsid, int page, String orderBy, String order, String filter){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question sets", "/questionsets"));
        return ok(questionSetManagement.render(selectedqsid, breadcrumbs, qsm.page(page, orderBy, order, filter), qsm, orderBy, order, filter, null));
    }

    public static Result create(){
        return TODO;
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
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question sets", "/questionsets"));
        breadcrumbs.add(new Link("Question set " + id, "/questionsets/:" + id));
        Form<QuestionSetModel> form = form(QuestionSetModel.class).bindFromRequest().fill(new QuestionSetManager().getFinder().ref(id));
        return ok(questionSetManagement.render(id, breadcrumbs, qsm.page(page, orderBy, order, filter), qsm, orderBy, order, filter, form));
    }

    public static Result update(String id){
        return TODO;
    }

    public static Result remove(String id){
        return TODO;
    }


}
