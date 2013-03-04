package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
	  
	  response().setHeader("X-Frame-Options", "SAMEORIGIN");
	  response().setHeader(CACHE_CONTROL, "max-age=3600");
	  return ok(index.render("Your new application is ready."));
  }
  
}