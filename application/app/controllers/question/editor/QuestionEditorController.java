package controllers.question.editor;

import java.util.ArrayList;
import java.util.List;

import models.data.Link;
import models.question.editor.RawQuestion;
import play.data.Form;
import play.mvc.Result;
import views.html.question.editor.create;
import views.html.question.editor.index;
import controllers.EController;

/**
 * This is the editor for creating question files, NOT the questions that will be saved in the database
 * @author Ruben Taelman
 *
 */
public class QuestionEditorController extends EController {

    // TODO If the links here won't change, it better for efficiency to define
    // these as a constant:
    //
    // private static List<Link> breadcrumbs = new ArrayList<List>();
    // static {
    //     breadcrumbs.add(new Link("Home", "/"));
    //     breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
    // }
    public static Result index(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        return ok(index.render(breadcrumbs));
    }
    
    public static Result create(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        breadcrumbs.add(new Link("Create", "/questioneditor/create"));
        
        Form<RawQuestion> questionForm = form(RawQuestion.class);
        return ok(create.render(breadcrumbs, questionForm));
    }
    
    public static Result save(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        breadcrumbs.add(new Link("Create", "/questioneditor/create"));
        
        Form<RawQuestion> questionForm = form(RawQuestion.class).bindFromRequest();
        
        if(questionForm.hasErrors()) {
            return badRequest(create.render(breadcrumbs, questionForm));
        } else {
            breadcrumbs.add(new Link(questionForm.field("addLanguage").value(), "/questioneditor/create"));
        }
        //questionForm.get().save();
        return ok(create.render(breadcrumbs, questionForm));
        //return redirect(routes.QuestionEditorController.create());
    }
}
