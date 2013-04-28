package controllers.data;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.validation.Valid;

import play.mvc.Result;

import controllers.EController;

import models.user.Role;
import models.user.AuthenticationManager;
import models.data.Link;
import models.data.manager.DataManager;
import models.data.links.LinkManager;
import models.data.grades.GradeManager;
import models.data.difficulties.DifficultyManager;

import views.html.data.data_view;
import views.html.commons.noaccess;

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

    private static boolean hasAccess() {
        return AuthenticationManager.getInstance().getUser()
            .hasRole(Role.DATAMANAGER);
    }

    /**
     * Show the current list of elements.
     * @param t The type of elements, for instance "links".
     */
    public static Result show(String t) {
        if(!hasAccess()) return ok(noaccess.render(breadcrumbs(t)));
        return fail(t, null);
    }

    /**
     * Show the current list of elements along with an error message.
     * @param t The type of elements, for instance "links".
     * @param exception The exception message, or null if none.
     */
    public static Result fail(String t, String exception) {
        if(!hasAccess()) return ok(noaccess.render(breadcrumbs(t)));
        return ok(data_view.render(
            managers.get(t),
            breadcrumbs(t),
            exception
        ));
    }

    /**
     * This method adds a new element of T to the list.
     * @param t The type of elements, for instance "links".
     */
     //* @param fields The strings describing the new element.
    public static Result add(String t) {
        if(!hasAccess()) return ok(noaccess.render(breadcrumbs(t)));
        FakeForm ff = form(FakeForm.class).bindFromRequest().get();
        String[] fields = ff.fields.toArray(new String[0]);
        try {
            managers.get(t).add(managers.get(t).createFromStrings(fields));
        } catch(GradeManager.CreationException e) {
            return fail(t, e.getEMessage());
        } catch(GradeManager.InsertionException e) {
            return fail(t, e.getEMessage());
        }
        return show(t);
    }

    /**
     * This method removes an element from the list.
     * @param t The type of elements, for instance "links".
     * @param id The id of the element.
     */
    public static Result remove(String t, String id) {
        if(!hasAccess()) return ok(noaccess.render(breadcrumbs(t)));
        try { managers.get(t).remove(id); }
        catch (GradeManager.RemovalException e) {
            return fail(t, e.getEMessage());
        }
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
