package controllers.question.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Link;
import models.question.Question;
import models.question.QuestionBuilderException;
import models.question.QuestionFactory;
import models.question.QuestionIO;
import models.question.QuestionType;
import models.user.UserID;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Play;
import play.libs.Json;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.question.editor.create;
import views.html.question.editor.index;
import controllers.EController;

/**
 * This is the editor for creating question files, NOT the questions that will be saved in the database
 * @author Ruben Taelman
 *
 */
public class QuestionEditorController extends EController {
    
    private static UserID getUserID() {
        // TODO: TMP !!!!
        UserID userID = new UserID("012345");
        return userID;
    }
    
    private static String getUserDownloadLocation(UserID userID) {
        return "/assets/files/"+userID.getUserID();
    }
    
    private static String getFileLocation(File file, UserID userID) {
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
        return ok(index.render(breadcrumbs));
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
        
        return ok(create.render(breadcrumbs, question));
    }
    
    /**
     * Upload of a file to be added to a question
     * @return
     */
    public static Result upload() {
        UserID userID = getUserID();
        
        // Prepare json main node
        ObjectNode result = Json.newObject();
        ArrayNode array = result.putArray("files");
        
        //RawBuffer raw = request().body().asRaw();
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart filePart = body.getFile("files[]");
        if(filePart !=null) {
            String name = filePart.getFilename();
            
            File file = filePart.getFile();
            
            File renamed = new File(QuestionIO.getUserUploadLocation(userID), name);
            file.renameTo(renamed);
            
            // Add file data to json result
            ObjectNode node = Json.newObject();
            node.put("name", name);
            node.put("size", renamed.length());
            node.put("url", getFileLocation(renamed, userID));
            array.add(node);
        }
        
        return ok(result);
    }
    
    /**
     * Deletion of a file in a question
     * @param name
     * @return
     */
    public static Result delete(String name) {
        File file = new File(QuestionIO.getUserUploadLocation(getUserID()), name);
        if(file.isFile())
            file.delete();
        return ok();
    }
    
    /**
     * Return the current files inside this question in json format
     * @return
     */
    public static Result getFiles() {
        UserID userID = getUserID();
        
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
    }
    
    /**
     * Validate the question by json input
     * @param json json encoded question
     * @return
     */
    public static Result validate(String json) {
        try {
            QuestionIO.validateJson(json);
            return ok("Valid question.");
        } catch (QuestionBuilderException e) {
            return badRequest(e.getMessage());
        }
    }
    
    /**
     * Export the question to a .ZIP file
     * @param json json encoded question
     * @return
     */
    public static Result export(String json) {
        response().setHeader("Content-Disposition", "attachment; filename=question.zip");
        UserID userID = getUserID();
        
        try {
            return ok(QuestionIO.export(json, userID, getUserDownloadLocation(userID)));
        } catch (QuestionBuilderException e) {
            return badRequest(e.getMessage());
        }
    }
    
    /**
     * Submits the question
     * param json json encoded question
     * @return
     */
    public static Result submit(String json){
        try {
            Question question = QuestionIO.validateJson(json);
            // TODO: submit the question
            return ok("Valid question.");
        } catch (QuestionBuilderException e) {
            return badRequest(e.getMessage());
        }
    }
}
