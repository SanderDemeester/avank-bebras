package controllers;

import java.util.ArrayList;

import models.data.Link;
import models.question.Question;
import models.question.QuestionBuilderException;
import models.question.QuestionIO;
import play.mvc.Result;
import views.html.index;
import views.html.test;

/**
 * @author Ruben Taelman
 * @author Sander Demeester
 */
public class Application extends EController {

  public static Result index() {
      Question q=null;
      try {
          q = QuestionIO.getFromXml("http://www.rubensworks.net/bebras/example_question_mc.xml");
      } catch (QuestionBuilderException e) {
        // TODO Auto-generated catch block
          return internalServerError(e.getMessage());
      }
      return ok(index.render(q.getID(), new ArrayList<Link>()));
  }

  public static Result test(String id){
      if(id=="link2")
          return ok(test.render("Link2"));
      else
          return ok(test.render(id));
  }
}
