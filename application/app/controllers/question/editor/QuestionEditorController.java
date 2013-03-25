package controllers.question.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.EMessages;
import models.data.Language;
import models.data.Link;
import models.data.UnavailableLanguageException;
import models.data.UnknownLanguageCodeException;
import models.question.MultipleChoiceElement;
import models.question.MultipleChoiceQuestion;
import models.question.Question;
import models.question.QuestionFactory;
import models.question.QuestionType;
import models.question.RegexQuestion;
import models.question.editor.RawQuestion;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Play;
import play.data.Form;
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

    // TODO If the links here won't change, it better for efficiency to define
    // these as a constant:
    //
    // private static List<Link> breadcrumbs = new ArrayList<List>();
    // static {
    //     breadcrumbs.add(new Link("Home", "/"));
    //     breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
    // }
    public static Result index(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        return ok(index.render(breadcrumbs));
    }
    
    public static Result create(String type){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("Question Editor", "/questioneditor"));
        breadcrumbs.add(new Link("Create "+EMessages.get("question.type."+type), ""));
        
        Question question = QuestionFactory.newQuestion(QuestionType.valueOf(type));
        
        // TMP:
        Language en = null;
        try {
            en = Language.getLanguage("en");
        } catch (UnavailableLanguageException e) {
            return redirect(routes.QuestionEditorController.index());
        } catch (UnknownLanguageCodeException e) {
            return redirect(routes.QuestionEditorController.index());
        }
        question.addLanguage(en);question.getLanguages();//!!!
        question.setTitle("test", en);
        question.setIndex("<b>test</b>", en);
        question.setFeedback("<b>test2</b>", en);
        //((MultipleChoiceQuestion)question).addElement(en, new MultipleChoiceElement("aaap"));
        
        /*Form<RawQuestion> questionForm = form(RawQuestion.class);
        try{
            questionForm.fill(new RawQuestion(type));
        } catch(IllegalArgumentException e) {
            return redirect(routes.QuestionEditorController.index());
        }*/
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
        
        Form<RawQuestion> questionForm = form(RawQuestion.class).bindFromRequest();
        
        if(questionForm.hasErrors()) {
            return badRequest(create.render(breadcrumbs, new RegexQuestion()));
        } else {
            breadcrumbs.add(new Link(questionForm.field("addLanguage").value(), "/questioneditor/create"));
        }
        //questionForm.get().save();
        return ok(create.render(breadcrumbs, new RegexQuestion()));
        //return redirect(routes.QuestionEditorController.create());
    }
    
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
    
    public static Result delete(String name) {
        File file = new File(Play.application().configuration().getString("questioneditor.upload"), name);
        if(file.isFile())
            file.delete();
        return ok();
    }
    
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
}
