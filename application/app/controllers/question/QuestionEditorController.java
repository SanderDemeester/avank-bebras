package controllers.question;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import models.EMessages;
import models.data.Link;
import models.question.Question;
import models.question.QuestionBuilderException;
import models.question.QuestionFactory;
import models.question.QuestionIO;
import models.question.QuestionType;
import models.user.AuthenticationManager;
import models.user.Role;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Results;
import views.html.question.editor.create;
import views.html.question.editor.index;
import controllers.EController;
import controllers.routes;

/**
 * This is the editor for creating question files, NOT the questions that will be saved in the database
 * @author Ruben Taelman
 *
 */
public class QuestionEditorController extends EController {
    
    /**
     * Check if the current user is authorized for this editor
     * @return is the user authorized
     */
    private static boolean isAuthorized() {
        return AuthenticationManager.getInstance().getUser().hasRole(Role.QUESTIONEDITOR);
    }
    
    /**
     * Get the ID of the current logged in user
     * @return the id of the user
     */
    private static String getUserID() {
        return AuthenticationManager.getInstance().getUser().getID();
    }
    
    /**
     * Returns the download location for a user ID
     * @param userID id from a user
     * @return location where the user can download his files with http
     */
    private static String getUserDownloadLocation(String userID) {
        return "/assets/files/"+userID;
    }
    
    /**
     * The location of a file in the download location for a user
     * @param file a certain file the user should be able to download
     * @param userID the id of the user
     * @return the exact location the user can access a file via http
     */
    private static String getFileLocation(File file, String userID) {
        return getUserDownloadLocation(userID)+"/"+file.getName();
    }
    
    /**
     * Make default breadcrumbs for this controller
     * @return default breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs() {
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("question.editor.name"), "/questioneditor"));
        return breadcrumbs;
    }
    
    /**
     * Index page to select a question type to create
     * @return
     */
    public static Result index(){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        if(isAuthorized())  return ok(index.render(breadcrumbs));
        else                return Results.redirect(routes.Application.index());
    }
    
    /**
     * Creation of a question type
     * @param type question type (QuestionType)
     * @return
     */
    public static Result create(String type){
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("forms.createArg", EMessages.get("question.type."+type)), ""));
        
        Question question = QuestionFactory.newQuestion(QuestionType.valueOf(type));
        
        if(isAuthorized())  return ok(create.render(breadcrumbs, question));
        else                return Results.redirect(routes.Application.index());
    }
    
    /**
     * Upload of a file to be added to a question
     * @return
     */
    public static Result upload() {
        if(isAuthorized()) {
            String userID = getUserID();
            
            // Prepare json main node
            ObjectNode result = Json.newObject();
            ArrayNode array = result.putArray("files");
            
            //RawBuffer raw = request().body().asRaw();
            MultipartFormData body = request().body().asMultipartFormData();
            FilePart filePart = body.getFile("files[]");
            if(filePart !=null) {
                String name = filePart.getFilename();
                
                File file = filePart.getFile();
                
                File renamed = QuestionIO.addTempFile(QuestionIO.getUserUploadLocation(userID), name);
                file.renameTo(renamed);
                
                // Add file data to json result
                ObjectNode node = Json.newObject();
                node.put("name", name);
                node.put("size", renamed.length());
                node.put("url", getFileLocation(renamed, userID));
                array.add(node);
            }
            
            return ok(result);
        } else {
            return forbidden();
        }
    }
    
    /**
     * Deletion of a file in a question
     * @param name
     * @return
     */
    public static Result delete(String name) {
        if(isAuthorized()) {
            File file = new File(QuestionIO.getUserUploadLocation(getUserID()), name);
            if(file.isFile())
                file.delete();
            return ok();
        } else {
            return forbidden();
        }
    }
    
    /**
     * Return the current files inside this question in json format
     * @return
     */
    public static Result getFiles() {
        if(isAuthorized()) {
            String userID = getUserID();
            
            // Prepare json main node
            ObjectNode result = Json.newObject();
            ArrayNode array = result.putArray("files");
            
            // Loop over all files in the upload folder
            String location = QuestionIO.getUserUploadLocation(userID);
            File folder = new File(location);
            for (File file : folder.listFiles()) {
                // Add file data to json result
                ObjectNode node = Json.newObject();
                node.put("name", file.getName());
                node.put("size", file.length());
                node.put("url", getFileLocation(file, userID));
                array.add(node);
            }
            
            return ok(result);
        } else {
            return forbidden();
        }
    }
    
    /**
     * Validate the question by json input
     * @param json json encoded question
     * @return
     */
    public static Result validate(String json) {
        if(isAuthorized()) {
            try {
                QuestionIO.validateJson(json);
                return ok("Valid question.");
            } catch (QuestionBuilderException e) {
                return badRequest(e.getMessage());
            }
        } else {
            return forbidden();
        }
    }
    
    /**
     * Export the question to a .ZIP file
     * @param json json encoded question
     * @return
     */
    public static Result export(String json) {
        if(isAuthorized()) {
            response().setHeader("Content-Disposition", "attachment; filename=question.zip");
            String userID = getUserID();
            
            try {
                return ok(QuestionIO.export(json, userID, getUserDownloadLocation(userID)));
            } catch (QuestionBuilderException e) {
                return badRequest(e.getMessage());
            }
        } else {
            return forbidden();
        }
    }
    
    /**
     * Upload of a file that has to be imported
     * @return
     */
    public static Result importUpload() {
        if(isAuthorized()) {
            String userID = getUserID();
            String upload = QuestionIO.getUserUploadLocation(userID);
            
            MultipartFormData body = request().body().asMultipartFormData();
            FilePart filePart = body.getFile("files[]");
            if(filePart !=null) {
                String name = filePart.getFilename();
                File file = filePart.getFile();
                ZipInputStream zis = null;
                try {
                    zis = new ZipInputStream(new FileInputStream(file));
                    Question question = QuestionIO.importUpload(zis, userID, getUserDownloadLocation(userID));
                    List<Link> breadcrumbs = defaultBreadcrumbs();
                    breadcrumbs.add(new Link(EMessages.get("forms.create"), ""));
                    return ok(create.render(breadcrumbs, question));
                } catch (QuestionBuilderException e) {
                    return badRequest(e.getMessage());
                } catch (IOException e) {
                    return badRequest(e.getMessage());
                } finally {
                    try {
                        zis.close();
                    } catch (IOException e) {
                        return badRequest(e.getMessage());
                    }
                }
            }
            
            return badRequest(EMessages.get("question.factory.error.invalidUpload"));
        } else {
            return forbidden();
        }
    }
    
    /**
     * Submits the question
     * param json json encoded question
     * @return
     */
    public static Result submit(String json){
        if(isAuthorized()) {
            String userID = getUserID();
            
            try {
                QuestionIO.submit(json, userID, getUserDownloadLocation(userID));
                return ok(EMessages.get("question.editor.submitted"));
            } catch (QuestionBuilderException e) {
                return badRequest(e.getMessage());
            }
        } else {
            return forbidden();
        }
    }
}
