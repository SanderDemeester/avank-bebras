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

import models.data.Link;
import models.dbentities.FAQModel;
import play.i18n.Lang;
import play.mvc.Result;
import views.html.faq.faq;
import controllers.EController;

//import views.faq.nofaq;

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
        
        List<FAQModel> f=null;
        //String l = Lang.get(); //TODO
        String l = "en";
        try{
        	f = Ebean.find(FAQModel.class).where().eq("language", l).findList();
        }catch(PersistenceException e){
        	//TODO
        }
        if(f==null || f.isEmpty()){
        	//TODO
        }
        return ok(faq.render(breadcrumbs,f));
  
	}
}
