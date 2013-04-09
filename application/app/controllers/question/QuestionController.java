package controllers.question;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.dbentities.QuestionModel;
import models.management.ModelState;
import models.question.QuestionManager;
import models.question.server.Server;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.Result;
import views.html.question.newQuestionForm;
import views.html.question.questionManagement;

import com.avaje.ebean.annotation.Transactional;

import controllers.EController;

/**
 * Actions for controlling the CRUD actions for questions (including approval)
 * @author Ruben Taelman
 *
 */
public class QuestionController extends EController{
    
    private static Finder<String, Server> serverFinder = new Finder<String, Server>(String.class, Server.class);
    
    public static Result LIST = redirect(
            routes.QuestionController.list(0, "id", "asc", "")
    );
    
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
    @Transactional(readOnly=true)
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
    
    // TODO: Add this to the question creation
    public static Result approve() throws Exception{
        Server server = Server.findById("avank.rubensworks.net");
        server.sendFile("questionid", new File("questionSubmits/rutaelman/question.zip~c77614c5ffbfe4f5a40a8a5ecef3eaf5rutaelman"), "rutaelman");
        return ok("ok");
    }
    
    /**
     * This result will redirect to the create a question page
     * containing the corresponding form.
     *
     * @return create a question page
     */
    @Transactional(readOnly=true)
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
    @Transactional
    public static Result save(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("question.questions.new"), "/questions/create"));
        
        Form<QuestionModel> form = form(QuestionModel.class).bindFromRequest();
        
        if(form.hasErrors()) {
            return badRequest(newQuestionForm.render(form, new QuestionManager(ModelState.CREATE), breadcrumbs));
        }
        
        form.get().save();
        
        flash("success", "Question " + form.get().officialid + " has been created!");
        return LIST;
    }
}
