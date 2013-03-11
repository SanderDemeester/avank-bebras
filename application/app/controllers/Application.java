package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends EController {
	
  
  public static Result index() {
	  setCommonHeaders();
	  
	  return ok(index.render("nanos gigantum humeris insidentes"));
  }

  public static Result test(String id){
	  
	  setCommonHeaders();
	  if(id=="link2")
		  return ok(test.render("Link2"));
	  else
		  return ok(test.render(id));
		  
  }
}