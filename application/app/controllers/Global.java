package controllers;

import play.GlobalSettings;
import play.api.*;
import play.mvc.*;
import views.html.*;
import static play.mvc.Results.*;

public class Global extends GlobalSettings{
	
	 public Result onError(Throwable t) {
	    return internalServerError(
	     views.html.index.render("fout")
	    );
	  }  
	 
	  public Result onActionNotFound(String uri) {
	    return notFound(
	      views.html.PageNotFound.render("This is not the page you are looking for")
	    );
	  }  
}
