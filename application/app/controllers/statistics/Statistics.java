package controllers.statistics;

import play.mvc.Result;

import controllers.EController;

import views.html.statistics.statistics;

/**
 * This class returns the statistics page for the logged in user.
 * @author Felix Van der Jeugt
 */
public class Statistics extends EController {

    public static Result statistics() {
        return ok(statistics.render());
    }

}
