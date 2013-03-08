package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends EController {
  
  public static Result index() {
	  setCommonHeaders();
	  return ok(index.render("nanos gigantum humeris insidentes"));
  }
  
}