package controllers.question;

import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.dbentities.QuestionModel;
import models.management.ModelState;
import models.question.QuestionManager;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Results;
import views.html.question.newQuestionForm;
import views.html.question.questionManagement;
import controllers.EController;
import controllers.question.routes;

/**
 * Actions for controlling the CRUD actions for questions (including approval)
 * @author Ruben Taelman
 *
 */
public class QuestionController extends EController{
    
    /**
     * Make default breadcrumbs for this controller
     * @return default breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs() {
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("question.questions.name"), "/questions"));
        return breadcrumbs;
    }
    
    /**
     * This result will redirect to the question list page
     *
     * @return question list page
     */
    public static Result list(int page, String orderBy, String order, String filter){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        
        QuestionManager questionManager = new QuestionManager(ModelState.READ);
        questionManager.setOrder(order);
        questionManager.setOrderBy(orderBy);
        questionManager.setFilter(filter);
        
        return ok(
            questionManagement.render(questionManager.page(page), questionManager, orderBy, order, filter, breadcrumbs)
        );
    }
    
    /**
     * This result will redirect to the create a question page
     * containing the corresponding form.
     *
     * @return create a question page
     */
    public static Result create(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questions.new"), "/questions/create"));
        
        Form<QuestionModel> form = form(QuestionModel.class).bindFromRequest();
        
        QuestionManager manager = new QuestionManager(ModelState.CREATE);
        manager.setIgnoreErrors(true);
        
        return ok(newQuestionForm.render(form, manager, breadcrumbs));
    }
    
    /**
     * This will handle the creation of a new question and redirect
     * to the question list
     *
     * @return question list page
     */
    public static Result save(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questions.new"), "/questions/create"));
        
        Form<QuestionModel> form = form(QuestionModel.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(newQuestionForm.render(form, new QuestionManager(ModelState.CREATE), breadcrumbs));
        }
        
        form.get().save();
        
        // TODO place message in flash for "question add warning" in view
        return ok("saved");
        //return Results.redirect(routes.QuestionController.list(0, "name", "asc", ""));
    }
}
