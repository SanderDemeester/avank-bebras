package controllers.competition;

import com.avaje.ebean.Ebean;
import controllers.EController;
import models.EMessages;
import models.competition.CompetitionClassManager;
import models.data.Link;
import models.dbentities.ClassGroup;
import models.dbentities.CompetitionModel;
import models.dbentities.ContestClass;
import models.management.ModelState;
import models.user.AuthenticationManager;
import play.data.Form;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Controller for crud operations on contest classes.
 *
 * @author Kevin Stobbelaar.
 */
public class CompetitionClassController extends EController {

    /**
     * Returns the default breadcrumbs for the contest pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(String contestid){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competition.breadcrumb"), "/contests"));
        breadcrumbs.add(new Link(EMessages.get("competition.class.breadcrumb"), "/contests/" +  contestid + "/classes"));
        return breadcrumbs;
    }

    /**
     * Returns a competition class manager object.
     * @param contestid contest id
     * @return competition class manager
     */
    private static CompetitionClassManager getManager(String contestid){
        String teacherid = AuthenticationManager.getInstance().getUser().getID();
        CompetitionClassManager manager = new CompetitionClassManager(ModelState.READ, "name", contestid, teacherid);
        manager.setFilter("");
        manager.setOrder("asc");
        manager.setOrderBy("name");
        return manager;
    }

    /**
     * Returns the page with the list of classes for the contest with id = contest id.
     * @param contestid contest id
     * @param page page number
     * @param orderBy order by
     * @param order order
     * @param filter filter
     * @return page with the list of classes
     */
    public static Result list(String contestid, int page, String orderBy, String order, String filter){
        CompetitionClassManager competitionClassManager = getManager(contestid);
        return ok(views.html.competition.classes.render(
                defaultBreadcrumbs(contestid),
                competitionClassManager.page(page),
                competitionClassManager,
                orderBy,
                order,
                filter));
    }

    /**
     * Returns the register a new class for this contest page.
     * @param contestid contest id
     * @return register new class page
     */
    public static Result register(String contestid){
        Form<ClassGroup> form = form(ClassGroup.class).bindFromRequest();
        List<Link> breadcrumbs = defaultBreadcrumbs(contestid);
        breadcrumbs.add(new Link(EMessages.get("competition.class.register.breadcrumb"), "/contests/" + contestid + "/classes/register" ));
        return ok(views.html.competition.registerClass.render(breadcrumbs, form, contestid));
    }

    public static LinkedHashMap<String, String> options() {
        String teacherid = AuthenticationManager.getInstance().getUser().getID();
        List<ClassGroup> classGroups = Ebean.find(ClassGroup.class).where().eq("teacherid", teacherid).findList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(ClassGroup classGroup: classGroups) {
            options.put(classGroup.getID(), classGroup.getName() + " - " + classGroup.getSchool().name);
        }
        return options;
    }

    /**
     * Saves the new class for the selected contest.
     * Returns to the contest classes page.
     * @param contestid contest id
     * @return contest classes page
     */
    public static Result save(String contestid){
        Form<ClassGroup> form = form(ClassGroup.class).bindFromRequest();
        String classId = form.field("class").value();
        ContestClass contestClass = new ContestClass();
        ClassGroup classGroup = Ebean.find(ClassGroup.class).where().idEq(Integer.parseInt(classId)).findUnique();
        CompetitionModel contest = Ebean.find(CompetitionModel.class).where().idEq(contestid).findUnique();
        ContestClass old = Ebean.find(ContestClass.class).where().eq("contestid", contest).eq("classid", classGroup).findUnique();
        if (old != null){
            List<Link> breadcrumbs = defaultBreadcrumbs(contestid);
            breadcrumbs.add(new Link(EMessages.get("competition.class.register.breadcrumb"), "/contests/" + contestid + "/classes/register" ));
            flash("error", EMessages.get("competition.class.register.error"));
            return badRequest(views.html.competition.registerClass.render(breadcrumbs, form, contestid));
        }
        contestClass.contestid = contest;
        contestClass.classid = classGroup;
        Ebean.save(contestClass);
        return redirect(routes.CompetitionClassController.list(contestid, 0, "name", "asc", ""));
    }

}
