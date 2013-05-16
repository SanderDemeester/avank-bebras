/**
 *
 */
package controllers.user;

import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.user.ChainOfCommand;

import controllers.EController;
import play.mvc.Result;
import views.html.commons.error;
import views.html.commons.noaccess;
import views.html.user.viewuser;

/**
 * @author Jens N. Rammant
 *
 */
public class OtherUserController extends EController{

    /**
     *
     * @return the page showing the actions you can do with that users
     */
    public static Result show(String userID){
        List<Link> bc = getBreadcrumbs(userID);
        try{
            if(!ChainOfCommand.isSuperiorOf(userID))return ok(noaccess.render(bc));
            return ok(viewuser.render(bc,userID));
        }catch(Exception e){
            return ok(error.render(bc, "Error", EMessages.get("error.text")));
        }
    }

    private static List<Link> getBreadcrumbs(String userID){
        List<Link> res = new ArrayList<Link>();
        res.add(new Link("Home","/"));
        res.add(new Link(userID, "/user/"+userID));
        return res;
    }
}
