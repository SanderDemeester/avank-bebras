/**
 * 
 */
package controllers.faq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Language;
import models.data.Link;
import models.dbentities.FAQModel;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Results;
import views.html.faq.faq;
import controllers.EController;
import controllers.faq.routes;

import views.html.faq.faqManagement;
import views.html.faq.newFAQForm;


/**
 * @author Jens N. Rammant
 *
 *TODO i18n (especially in templates)
 */
public class FAQController extends EController {
	
	/*
	 * @return the FAQ in the correct language if available;
	 */
	public static Result getFAQ(){
		setCommonHeaders();
		
		List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link("FAQ","/FAQ"));
        
        
        List<FAQModel> f=new ArrayList<FAQModel>();
        String l = EMessages.getLang(); //Retrieve the user's language
        try{
        	//Retrieve all FAQ elements in that language
        	f.addAll(Ebean.find(FAQModel.class).where().eq("language", l).findList());
        }catch(PersistenceException e){
        	//add a message to say something went wrong
        	f.clear();
        	FAQModel temp = new FAQModel();
        	temp.name = EMessages.get("faq.error");
        	f.add(temp);
        }
        if(f.isEmpty()){
        	//add a message to say the FAQ is empty
        	FAQModel temp = new FAQModel();
        	temp.name = EMessages.get("faq.empty");
        	f.add(temp);
        }
        return ok(faq.render(breadcrumbs,f));
  
	}

	/**
     * This result will redirect to the FAQ list page
     *
     * @return faq list page
     */
	public static Result list(int page, String orderBy, String order, String filter){
		//TODO check permissions + breadcrumbs
		FAQManager fm = new FAQManager();
		return ok( //TODO try-catch
	            faqManagement.render(fm.page(page, orderBy, order, filter), fm, orderBy, order, filter, new ArrayList<Link>())
	        );
	}
	
	public static Result create(){
		//TODO check permissions
		Form<FAQModel> form = form(FAQModel.class).bindFromRequest();
		Map<String,String> languages = new HashMap<String,String>();
		for (Language l : Language.listLanguages()){
			languages.put(l.getCode(), l.getName());
		}
	    return ok(newFAQForm.render(form, new ArrayList<Link>(),languages));

		
	    
	}
	
	public static Result save(){
		//TODO check permissions
		Form<FAQModel> form = form(FAQModel.class).bindFromRequest();
        if(form.hasErrors()) {
        	Map<String,String> languages = new HashMap<String,String>();
    		for (Language l : Language.listLanguages()){
    			languages.put(l.getCode(), l.getName());
    		}
            return badRequest(newFAQForm.render(form, new ArrayList<Link>(),languages));
        }
        FAQModel m = form.get();
        m.save(); //TODO try-catch
        return Results.redirect(routes.FAQController.list(0, "name", "asc", ""));
	}
	
	public static Result edit(String id){
		//TODO check permissions
		//TODO
		return null;
	}
	
	public static Result update(String id){
		//TODO check permissions
		//TODO
		return null;
	}
	
	public static Result remove(String id){
		//TODO check permissions, confirmation screen
		FAQModel fm = (FAQModel) new FAQManager().getFinder().byId(id);
		//TODO try-catch
        fm.delete();
        return redirect(routes.FAQController.list(0, "language", "asc", ""));
	}
}
