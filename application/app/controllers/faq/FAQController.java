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

import models.util.OperationResultInfo;
import play.mvc.Result;
import play.mvc.Results;
import views.html.faq.faq;
import controllers.EController;
import controllers.faq.routes;

import views.html.faq.faqManagement;
import views.html.faq.newFAQForm;
import views.html.faq.alterFAQForm;


/**
 * @author Jens N. Rammant
 * TODO: put link to manageFAQ somewhere (admin control panel)
 */
public class FAQController extends EController {
	
	/**
	 * @return the FAQ in the correct language if available;
	 */
	public static Result getFAQ(){
		//Creating breadcrumbs
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
        	f.clear(); //TODO use new view
        	FAQModel temp = new FAQModel();
        	temp.name = EMessages.get("faq.error");
        	f.add(temp);
        }
        if(f.isEmpty()){
        	//add a message to say the FAQ is empty
        	FAQModel temp = new FAQModel();//TODO use new view
        	temp.name = EMessages.get("faq.empty");
        	f.add(temp);
        }
        return ok(faq.render(breadcrumbs,f));
  
	}

	/**
	 * Returns the list of FAQs
	 * @param page Page or results to be displayed
	 * @param orderBy What field to order on
	 * @param order Which order the results have to be in
	 * @param filter Filter to be used on the results
	 * @param info	Info messages to be displayed
	 * @return
	 */
	public static Result list(int page, String orderBy, String order, String filter,OperationResultInfo info){
		//TODO check permissions 
				
		//Creation of breadcrumbs
		List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("faq.managefaq"),"/manageFAQ"));
		
		FAQManager fm = new FAQManager();
		try{
			//Try to render the list
			return ok(
	            faqManagement.render(fm.page(page, orderBy, order, filter), fm, orderBy, order, filter, breadcrumbs, info)
	        );
		}catch(Exception e){
			//If fails, show no list (page = null) but display an error.
			info.add(EMessages.get("faq.list.error"),OperationResultInfo.Type.ERROR);
			return ok(
					faqManagement.render(null, fm, orderBy, order, filter, breadcrumbs, info)
					);
		}
	}
	
	/*
	 * Same as the other one, but uses an empty OperationResultInfo
	 */
	public static Result list(int page, String orderBy, String order, String filter){
		return list(page,orderBy,order,filter,new OperationResultInfo());
	}
	
	
	
	/**
     * This result will redirect to the create a new FAQ page
     *
     * @return faq creation page
     */
	public static Result create(){
		//TODO check permissions
		
		//Creation of breadcrumbs
		List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("faq.managefaq"),"/manageFAQ"));
        breadcrumbs.add(new Link(EMessages.get("faq.addfaq"),"/manageFAQ/new"));
		
		Form<FAQModel> form = form(FAQModel.class).bindFromRequest();
		
		//Retrieve list of languages
		Map<String,String> languages = new HashMap<String,String>();
		for (Language l : Language.listLanguages()){
			languages.put(l.getCode(), l.getName());
		}
		
		//Return the form for a new FAQ
	    return ok(newFAQForm.render(form, breadcrumbs,languages, new OperationResultInfo()));

		
	    
	}
	/**
	 * This will save the result from the form and then redirect to the list page
	 * @return FAQ list page
	 */
	public static Result save(){
		//TODO check permissions
		
		//Generate breadcrumbs
		List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("faq.managefaq"),"/manageFAQ"));
        breadcrumbs.add(new Link(EMessages.get("faq.addfaq"),"/manageFAQ/new"));
        
        //Generate list of languages
        Map<String,String> languages = new HashMap<String,String>();
		for (Language l : Language.listLanguages()){
			languages.put(l.getCode(), l.getName());
		}
		
		//Retrieve the form
		Form<FAQModel> form = form(FAQModel.class).bindFromRequest();
        if(form.hasErrors()) {        	
        	//Form was not complete --> return form with a warning
    		OperationResultInfo ori = new OperationResultInfo();
    		ori.add(EMessages.get("faq.error.notcomplete"), OperationResultInfo.Type.WARNING);
            return badRequest(newFAQForm.render(form, breadcrumbs,languages, ori));
        }
        //Try to save the info
        FAQModel m = form.get();
        try{
        	m.save();
        }catch(Exception p){
        	//Something went wrong in the saving. Redirect back to the create page with an error alert
        	OperationResultInfo ori = new OperationResultInfo();
    		ori.add(EMessages.get("faq.error.savefail"), OperationResultInfo.Type.ERROR);
            return badRequest(newFAQForm.render(form, breadcrumbs,languages, ori));
        }
        //Redirect back to the list
        return Results.redirect(routes.FAQController.list(0, "name", "asc", ""));
	}
	
	/**
	 * Shows the edit page for a certain FAQModel
	 * @param id of the to be altered FAQModel
	 * @return FAQ alter page
	 */
	public static Result edit(String id){
		//TODO check permissions
		
		//Generate breadcrumbs
		List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("faq.managefaq"),"/manageFAQ"));
        breadcrumbs.add(new Link(EMessages.get("faq.alter"),"/manageFAQ/"+id));
		
        //Generate list of languages
		Map<String,String> languages = new HashMap<String,String>();
		for (Language l : Language.listLanguages()){
			languages.put(l.getCode(), l.getName());
		}
		
		//Try to render a form from the to-be-edited FAQModel
		Form<FAQModel> form = form(FAQModel.class).bindFromRequest().fill((FAQModel) new FAQManager().getFinder().ref(id));
        try{
        	Result r = 
				ok(alterFAQForm.render(form, breadcrumbs,languages,id, new OperationResultInfo()));
        	return r;
        }catch(Exception e){
        	//Something went wrong during the creation of the form (e.g. no database connection
        	//Redirect back to the list page with an error message
        	OperationResultInfo inf = new OperationResultInfo();
        	inf.add(EMessages.get("faq.error"), OperationResultInfo.Type.ERROR);
        	return list(0, "name", "asc", "",inf);
        }
	}
	
	/**
     * This will handle the update of an existing FAQ and redirect
     * to the FAQ list
     *
     * @param id id of the FAQ to be updated
     * @return FAQ list page
     */
    public static Result update(String id){
    	//TODO check permissions
    	
    	//Generate breadcrumbs
    	List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("faq.managefaq"),"/manageFAQ"));
        breadcrumbs.add(new Link(EMessages.get("faq.alter"),"/manageFAQ/"+id));
        
        //Generate list of languages
        Map<String,String> languages = new HashMap<String,String>();
		for (Language l : Language.listLanguages()){
			languages.put(l.getCode(), l.getName());
		}
		
		//Try to update the FAQModel from the form
		Form<FAQModel> form = null;
        try{
        	 form = form(FAQModel.class).fill((FAQModel) new FAQManager().getFinder().byId(id)).bindFromRequest();
        }catch(Exception e){
        	//Something went wrong with the filling of the FAQModel (e.g. no database connection)
        	//Redirect back to the list of FAQ with an error message
        	//(Can't go back to the edit-page because the form was incorrectly created)
        	OperationResultInfo inf = new OperationResultInfo();
        	inf.add(EMessages.get("faq.error"), OperationResultInfo.Type.ERROR);
        	return list(0, "name", "asc", "",inf);
        }
        if(form.hasErrors()) {
        	//Form was incomplete. Show the edit-page again with an error
    		OperationResultInfo ori = new OperationResultInfo();
    		ori.add(EMessages.get("faq.error.notcomplete"),OperationResultInfo.Type.WARNING);
            return badRequest(alterFAQForm.render(form, breadcrumbs,languages,id, ori));
        }
        //Try to save the updated FAQModel
        FAQModel updated = form.get();
        updated.id = Integer.parseInt(id);
        try{
        	updated.update();
        }catch(Exception p){
        	//Something went wrong in the saving. Redirect back to the create page with an error alert
        	OperationResultInfo ori = new OperationResultInfo();
    		ori.add(EMessages.get("faq.error.savefail"), OperationResultInfo.Type.ERROR);
            return badRequest(alterFAQForm.render(form, breadcrumbs,languages, id, ori));
        }
        //Return to the list of FAQ
        return redirect(routes.FAQController.list(0, "name", "asc", ""));
    }
	
	/**
	 * This removes a FAQ from the list and then redirects to the FAQ list page
	 * @param id The ID of the FAQ to be deleted
	 * @return FAQ list page
	 */
	public static Result remove(String id){
		//TODO check permissions
		
		//Try to remove the FAQModel
		try{
			FAQModel fm = (FAQModel) new FAQManager().getFinder().byId(id);
			fm.delete();
		}catch(Exception e){
			//Deleting unsuccessful, return the list with an error
			OperationResultInfo inf = new OperationResultInfo();
        	inf.add(EMessages.get("faq.remove.error"), OperationResultInfo.Type.ERROR);
        	return list(0, "name", "asc", "",inf);
		}
        return redirect(routes.FAQController.list(0, "language", "asc", ""));
	}
	
	/**
	 * Returns whether the current user is authorized to edit the FAQ
	 * (Everyone is authorized to view the FAQ)
	 * @return whether the current user is authorized to manage the FAQ
	 */
	public static boolean isAuthorized(){
		//TODO
		return true;
	}
}
