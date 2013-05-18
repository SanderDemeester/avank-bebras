package controllers.classgroups.pupilview;

import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.management.ModelState;
import models.user.AuthenticationManager;
import models.user.Independent;
import models.user.Role;
import play.mvc.Result;
import views.html.classes.pupilviews.pupilclasses;
import views.html.commons.noaccess;
import controllers.EController;

/**
 * @author Jens N. Rammant
 *
 */
public class PupilClassController extends EController {

    /**
     * view classes page
     * @param page page nr
     * @param orderBy order field
     * @param order ordering
     * @param filter filter
     * @return list view
     */
    public static Result viewClasses(int page, String orderBy, String order, String filter){
        List<Link> bc = getBreadcrumbs();
        if(!isAuthorized())return ok(noaccess.render(bc));
        try{
            Independent current = (Independent)AuthenticationManager.getInstance().getUser();
            PupilClassManager pcm = new PupilClassManager(current.getID(),
                    ModelState.READ);
            pcm.setOrder(order);
            pcm.setFilter(filter);
            pcm.setOrderBy(orderBy);
            return ok(pupilclasses.render(pcm.page(page), pcm, orderBy, order,
                    filter, bc, current.getCurrentClass()));
        }catch(Exception e){
            flash("error",EMessages.get("error.text"));
            return ok(pupilclasses.render(null, null, orderBy, order, filter, bc, null));
        }

    }

    private static boolean isAuthorized(){
        return AuthenticationManager.getInstance().getUser().hasRole(Role.PUPILCLASSVIEW);
    }

    private static List<Link> getBreadcrumbs(){
        ArrayList<Link> res = new ArrayList<Link>();
        res.add(new Link("Home","/"));
        res.add(new Link("classes.pupil.classes.list","/pclasses/view"));
        return res;
    }
}
