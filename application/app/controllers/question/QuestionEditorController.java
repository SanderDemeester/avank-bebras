package controllers.question;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import models.data.Link;
import play.mvc.Result;
import views.html.question.questionEditorIndex;
import controllers.EController;

/**
 * This is the editor for creating question files, NOT the questions that will be saved in the database
 * @author Ruben Taelman
 *
 */
public class QuestionEditorController extends EController {
    
    public static Result index(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        return ok(questionEditorIndex.render(breadcrumbs));
    }
}
