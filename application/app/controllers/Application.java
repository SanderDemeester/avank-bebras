package controllers;

import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.*;
import models.dbentities.CompetitionModel;
import play.data.Form;
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
        breadcrumbs.add(new Link("Info", "/info"));
    }

    /**
     * Show the index info page
     * @return the index page
     */
    public static Result index() {
        return ok(index.render("Nothing here yet...", breadcrumbs));
    }

    /**
     * Change the language in this session
     * @param code language code to change the language to
     * @return redirect to the old page, but now translated
     */
    public static Result changeLanguage(String code) {
        try {
            Language.getLanguage(code);
            EMessages.setLang(code);
        } catch (UnavailableLanguageException | UnknownLanguageCodeException e) {}
        String referrer = request().getHeader("referer");
        if(referrer != null)
            return redirect(referrer);
        else
            return redirect(routes.Application.index());
    }
}
