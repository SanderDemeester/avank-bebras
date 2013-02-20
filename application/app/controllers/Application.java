package controllers;

import models.Bar;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;

import views.html.*;

import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Sander Demeester"));
    }

    public static Result addBar(){
        Bar bar = Form.form(Bar.class).bindFromRequest().get();
        bar.save();

        return redirect(routes.Application.index());
    }

    public static Result getBars(){
        List<Bar> bars = new Model.Finder(String.class,Bar.class).all();
        return  ok(toJson(bars));
    }
  
}
