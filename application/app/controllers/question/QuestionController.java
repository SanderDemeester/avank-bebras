package controllers.question;

import java.util.ArrayList;

import models.data.Link;
import models.dbentities.QuestionModel;
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
     * This result will redirect to the question list page
     *
     * @return question list page
     */
    public static Result list(int page, String orderBy, String order, String filter){
        QuestionManager questionManager = new QuestionManager();
        return ok(
            questionManagement.render(questionManager.page(page, orderBy, order, filter), questionManager, orderBy, order, filter, new ArrayList<Link>())
        );
    }
    
    /**
     * This result will redirect to the create a question page
     * containing the corresponding form.
     *
     * @return create a question page
     */
    public static Result create(){
        Form<QuestionModel> form = form(QuestionModel.class).bindFromRequest();
        return ok(newQuestionForm.render(form, new ArrayList<Link>()));
    }
    
    /**
     * This will handle the creation of a new question and redirect
     * to the question list
     *
     * @return question list page
     */
    public static Result save(){
        Form<QuestionModel> form = form(QuestionModel.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(newQuestionForm.render(form, new ArrayList<Link>()));
        }
        
        new QuestionModel().save();
        //form.get().save();
        
        // TODO place message in flash for "question add warning" in view
        
        return Results.redirect(routes.QuestionController.list(0, "name", "asc", ""));
    }
}
