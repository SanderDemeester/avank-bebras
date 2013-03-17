package controllers;

import models.question.Question;
import models.question.QuestionBuilderException;

import play.mvc.Result;

import views.html.*;

/**
 * @author Ruben Taelman
 * @author Sander Demeester
 */
public class Application extends EController {

  public static Result index() {
      setCommonHeaders();

      Question q=null;
      try {
          q = Question.getFromXml("http://www.rubensworks.net/bebras/example_question_mc.xml");
      } catch (QuestionBuilderException e) {
        // TODO Auto-generated catch block
          return internalServerError(e.getMessage());
      }

      return ok(index.render(q.getID()));
  }

  public static Result test(String id){
      setCommonHeaders();

      if(id=="link2")
          return ok(test.render("Link2"));
      else
          return ok(test.render(id));
  }
}
