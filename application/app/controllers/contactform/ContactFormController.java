/**
 * 
 */
package controllers.contactform;

import java.util.ArrayList;

import models.EMessages;
import models.data.Link;
import models.user.AuthenticationManager;
import models.user.User;

import play.mvc.Result;
import play.data.Form;
import play.data.validation.Constraints;
import views.html.contactform.contact;
import controllers.EController;


/**
 * @author Jens N. Rammant
 *
 */
public class ContactFormController extends EController {

	/**
	 * 
	 * @return a page with a contactform
	 */
	public static Result showContactForm(){
		//Generate breadcrumbs
		ArrayList<Link> bc = new ArrayList<Link>();
		bc.add(new Link("Home","/"));
		bc.add(new Link(EMessages.get("contact.formtitle"),"/contact"));			
		
		Form<ContactForm> f = form(ContactForm.class);	
		User current = AuthenticationManager.getInstance().getUser();
		//Fill in the logged in user's emailaddress (if there is one)
		if(current!=null && current.data!=null && current.data.email!=null){
			ContactForm cf = new ContactForm();
			cf.email = current.data.email;
			f=f.fill(cf);
		}
		return ok(contact.render(f,bc));
		
	}
	
	/**
	 * Tries to send the data in the contactform to the appropriate emailaddress
	 * @return a page with the contactform
	 */
	public static Result saveContactForm(){
		//Generate breadcrumbs
		ArrayList<Link> bc = new ArrayList<Link>();
		bc.add(new Link("Home","/"));
		bc.add(new Link(EMessages.get("contact.formtitle"),"/contact"));
		
		Form<ContactForm> f = form(ContactForm.class).bindFromRequest();
		if(f.hasErrors()){
			flash("warning",EMessages.get("contact.form.incomplete"));
			return badRequest(contact.render(f, bc));
		}
		//TODO actual sending
		return redirect(routes.ContactFormController.showContactForm());
	}
	
	public static class ContactForm{
		@Constraints.Required
		@Constraints.Email
		public String email;
		@Constraints.Required		
		public String question;
	}
}
