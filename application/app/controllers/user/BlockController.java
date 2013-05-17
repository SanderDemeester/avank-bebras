/**
 *
 */
package controllers.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Link;
import models.dbentities.UserModel;
import models.user.ChainOfCommand;

import play.data.Form;
import play.data.format.Formats;
import play.mvc.Result;
import views.html.commons.error;
import views.html.commons.noaccess;
import views.html.user.blockuser;
import controllers.EController;

/**
 * @author Jens N. Rammant
 *
 */
public class BlockController extends EController {

    /**
     *
     * @param id
     *            of the user to block
     * @return a page with a inputfield for blockedUntil date
     */
    public static Result show(String id) {
        List<Link> bc = getBreadcrumbs(id);
        try {
            if (!ChainOfCommand.isSuperiorOf(id))
                return ok(noaccess.render(bc));
            UserModel toBlock = Ebean.find(UserModel.class, id);
            BlockDateWrapper bdw = new BlockDateWrapper();
            bdw.blockedTill = toBlock.blockeduntil;
            Form<BlockDateWrapper> f = form(BlockDateWrapper.class).fill(bdw);
            return ok(blockuser.render(f, bc, id));
        } catch (Exception e) {
            return ok(error.render(bc, "Error", EMessages.get("error.text")));
        }
    }

    public static Result block(String id) {
        List<Link> bc = getBreadcrumbs(id);
        try {
            if (!ChainOfCommand.isSuperiorOf(id))
                return ok(noaccess.render(bc));
            Form<BlockDateWrapper> f = form(BlockDateWrapper.class)
                    .bindFromRequest();
            if (f.hasErrors())
                return badRequest(blockuser.render(f, bc, id));
            UserModel toChange = Ebean.find(UserModel.class, id);
            toChange.blockeduntil = f.get().blockedTill;
            toChange.update();
            return redirect(routes.OtherUserController.show(id));
        } catch (Exception e) {
            return ok(error.render(bc, "Error", EMessages.get("error.text")));
        }
    }

    private static List<Link> getBreadcrumbs(String userID){
        ArrayList<Link> res = new ArrayList<Link>();
        res.add(new Link("Home","/"));
        res.add(new Link(userID, "/user/"+userID));
        res.add(new Link("otheruser.block","/user/"+userID+"/block"));
        return res;
    }


    public static class BlockDateWrapper{
        @Formats.DateTime(pattern = "dd/MM/yyyy")
        public Date blockedTill;
    }

}

