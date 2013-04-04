/**
 * 
 */
package controllers.faq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.swing.text.html.HTML;

import com.avaje.ebean.Ebean;

import models.EMessages;
import models.data.Language;
import models.data.Link;
import models.dbentities.FAQModel;
import play.i18n.Lang;
import play.mvc.Result;
import views.html.faq.faq;
import controllers.EController;


/**
 * @author Jens N. Rammant
 *
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
}
