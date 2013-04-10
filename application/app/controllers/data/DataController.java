package controllers.data;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.validation.Valid;

import play.mvc.Result;

import controllers.EController;

import models.data.Link;
import models.data.manager.DataElement;
import models.data.manager.DataManager;
import models.data.links.LinkManager;
import models.data.grades.GradeManager;
import models.data.difficulties.DifficultyManager;

import views.html.data.data_view;

/**
 * Controls the managing of a dataManager.
 * @author Felix Van der Jeugt
 */
public class DataController extends EController {

    protected static Map<String, DataManager<?>> managers
        = new HashMap<String, DataManager<?>>();

    static {
        managers.put("links",        new LinkManager());
        managers.put("grades",       new GradeManager());
        managers.put("difficulties", new DifficultyManager());
    }

    /**
     * Show the current list of elements.
     * @param t The type of elements, for instance "links".
     */
    public static Result show(String t) {
        return ok(data_view.render(
            managers.get(t),
            breadcrumbs(t)
        ));
    }

    /**
     * This method adds a new element of T to the list.
     * @param t The type of elements, for instance "links".
     */
     //* @param fields The strings describing the new element.
    public static Result add(String t) {
        FakeForm ff = form(FakeForm.class).bindFromRequest().get();
        String[] fields = ff.fields.toArray(new String[0]);
        managers.get(t).add(managers.get(t).createFromStrings(fields));
        return show(t);
    }

    /**
     * This method removes an element from the list.
     * @param t The type of elements, for instance "links".
     * @param id The id of the element.
     */
    public static Result remove(String t, String id) {
        managers.get(t).remove(id);
        return show(t);
    }

    /** The breadcrumbs. */
    public static List<Link> breadcrumbs(String t) {
        List<Link> crumbs = new ArrayList<Link>();
        crumbs.add(new Link("Home", "/"));
        crumbs.add(new Link(managers.get(t).title(), "/manage/" + managers.get(t).url()));
        return crumbs;
    }

    public static class FakeForm {
        @Valid public List<String> fields;
        public FakeForm() {}
    }

}
