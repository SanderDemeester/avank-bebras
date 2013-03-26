package controllers.question.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import models.EMessages;
import models.data.Link;
import models.question.Question;
import models.question.QuestionBuilderException;
import models.question.QuestionFactory;
import models.question.QuestionType;
import models.question.RegexQuestion;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.w3c.dom.Document;

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
    
    /**
     * Make default breadcrumbs for this controller
     * @return default breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs() {
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
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
        breadcrumbs.add(new Link("Create "+EMessages.get("question.type."+type), ""));
        
        Question question = QuestionFactory.newQuestion(QuestionType.valueOf(type));
        
        return ok(create.render(breadcrumbs, question));
    }
    
    public static Result save(){
        /**
         * TODO!!!! json save
         */
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        breadcrumbs.add(new Link("Create", ""));
        
        //questionForm.get().save();
        return ok(create.render(breadcrumbs, new RegexQuestion()));
        //return redirect(routes.QuestionEditorController.create());
    }
    
    /**
     * Upload of a file to be added to a question
     * @return
     */
    public static Result upload() {
        // Prepare json main node
        ObjectNode result = Json.newObject();
        ArrayNode array = result.putArray("files");
        
        //RawBuffer raw = request().body().asRaw();
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart filePart = body.getFile("files[]");
        if(filePart !=null) {
            String name = filePart.getFilename();
            
            File file = filePart.getFile();
            
            File renamed = new File(Play.application().configuration().getString("questioneditor.upload"), name);
            file.renameTo(renamed);
            
            // Add file data to json result
            ObjectNode node = Json.newObject();
            node.put("name", name);
            node.put("size", renamed.length());
            node.put("url", "/assets/files/"+renamed.getName());
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
        File file = new File(Play.application().configuration().getString("questioneditor.upload"), name);
        if(file.isFile())
            file.delete();
        return ok();
    }
    
    /**
     * Return the current files inside this question in json format
     * @return
     */
    public static Result getFiles() {
        // TODO: Make this specific for each user
        
        // Prepare json main node
        ObjectNode result = Json.newObject();
        ArrayNode array = result.putArray("files");
        
        // Loop over all files in the upload folder
        String location = Play.application().configuration().getString("questioneditor.upload");
        File folder = new File(location);
        for (File file : folder.listFiles()) {
            // Add file data to json result
            ObjectNode node = Json.newObject();
            node.put("name", file.getName());
            node.put("size", file.length());
            node.put("url", "/assets/files/"+file.getName());
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
            Question.validateJson(json);
            return ok("Valid question.");
        } catch (QuestionBuilderException e) {
            return badRequest(e.getMessage());
        }
    }
    
    public static Result export(String json) {
        response().setHeader("Content-Disposition", "attachment; filename=question.zip");
        try {
            return ok(Question.export(json));
        } catch (QuestionBuilderException e) {
            return badRequest(e.getMessage());
        }
    }
    
    /**
     * TO MOVE
     * @param doc
     * @return
     */
    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }
    
 // This method writes a DOM document to a file
    public static void writeXmlFile(Document doc, String filename) {
        try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(doc);
 
            // Prepare the output file
            File file = new File(filename);
            javax.xml.transform.Result result = new StreamResult(file);
 
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            xformer.setOutputProperty(OutputKeys.METHOD, "xml");
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }
    }
}
