package controllers.contactform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.mail.UpgradeRequestMail;
import models.user.AuthenticationManager;
import models.user.Role;
import models.user.User;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import scala.actors.threadpool.Arrays;
import views.html.commons.noaccess;
import views.html.contactform.upgradeRequest;
import controllers.EController;

/**
 * @author Jens N. Rammant
 *
 */
@SuppressWarnings("unchecked")
public class RequestTeacherController extends EController {
	
    /**
     *
     * @return a page to upload the image
     */

    private static final List<String> ALLOWED_FILE_TYPES;

    static{
        String[] toAdd = {"jpg","bmp","png","gif","jpeg"};
        ALLOWED_FILE_TYPES = Arrays.asList(toAdd);
    }

	/**
     * Show request teacher form
     * @return a page to upload the image
     */
    public static Result showForm(){
        //Generate breadcrumbs
        List<Link> breadcrumbs = getBreadcrumbs();
        if(!isAuthorized())return ok(noaccess.render(breadcrumbs));
        //Check if the user has an emailaddress
        User current = AuthenticationManager.getInstance().getUser();
        if(current==null||current.data==null||current.data.email==null){
            flash("warning",EMessages.get("contact.requestupgrade.noemail"));
        }
        //return page
        return ok(upgradeRequest.render(breadcrumbs));
    }

	/**
	 * Tries to send the mail, if possible
	 * 
	 * @return the upload page
	 */
    public static Result upload() {
        // Generate breadcrumbs
        List<Link> breadcrumbs = getBreadcrumbs();
        if (!isAuthorized())
            return ok(noaccess.render(breadcrumbs));
        // Check if current user has emailaddress
        User current = AuthenticationManager.getInstance().getUser();
        if (current == null || current.data == null
                || current.data.email == null) {
            flash("warning", EMessages.get("contact.requestupgrade.noemail"));
            return redirect(routes.RequestTeacherController.showForm());
        }
        // Retrieve uploaded data
        try {
            MultipartFormData data = request().body().asMultipartFormData();

            FilePart card = data.getFile("card");
            File file = card.getFile();
            if (!isImage(card.getFilename())) {
                flash("warning",
                        EMessages.get("contact.requestupgrade.notimage"));
                return redirect(routes.RequestTeacherController.showForm());
            }
            // Create and try to send mail
            UpgradeRequestMail urm = new UpgradeRequestMail(file,card.getFilename());

            urm.send();
            flash("success", EMessages.get("contact.form.sendsuccess"));

        } catch (Exception e) {
            flash("error", EMessages.get("contact.form.couldnotsend"));
        }
        return redirect(routes.RequestTeacherController.showForm());
    }

    /**
     *
     * @return list with the breadcrumb links
     */
    private static List<Link> getBreadcrumbs(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home","/"));
        breadcrumbs.add(new Link(EMessages.get("contact.upgraderequest"),"/upgrade"));
        return breadcrumbs;
    }

    /**
     *
     * @return if user is authorized
     */
    private static boolean isAuthorized(){
        return AuthenticationManager.getInstance().getUser().hasRole(Role.UPGRADETOTEACHER);
    }

    /**
     * Checks wether a file is an image using the extension
     * @param file filename of the file to check
     * @return whether file is an image
     */
    private static boolean isImage(String fileName){
        if(fileName==null||fileName.isEmpty())return false;
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length-1];
        return ALLOWED_FILE_TYPES.contains(extension.toLowerCase());

    }
}
