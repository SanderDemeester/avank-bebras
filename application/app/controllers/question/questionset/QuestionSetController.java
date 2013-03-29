package controllers.question.questionset;

import models.data.Link;
import models.question.questionset.QuestionSetManager;
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

    public static Result overview(String selectedqsid, int page, String orderBy, String order, String filter){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question sets", "/questionsets"));
        QuestionSetManager qsm = new QuestionSetManager();
        return ok(questionSetManagement.render(selectedqsid, breadcrumbs, qsm.page(page, orderBy, order, filter), qsm, orderBy, order, filter));
    }

    public static Result create(){
        return TODO;
    }

    public static Result edit(String id){
        return TODO;
    }

    public static Result remove(String id){
        return TODO;
    }


}
