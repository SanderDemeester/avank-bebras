package controllers;

import java.util.ArrayList;
import java.util.List;

import models.data.Link;
import models.question.Question;
import models.question.QuestionBuilderException;
import models.question.QuestionIO;
import play.mvc.Result;
import views.html.index;

/**
 * @author Ruben Taelman
 * @author Sander Demeester
 */
public class Application extends EController {
    
    private static List<Link> breadcrumbs = new ArrayList<Link>();
    static {
        breadcrumbs.add(new Link("Home", "/"));
    }

    public static Result index() {
        return ok(index.render("Nothing here yet...", breadcrumbs));
    }
}
