/**
 * 
 */
package controllers.faq;

import controllers.faq.routes;
import play.db.ebean.Model.Finder;
import play.mvc.Call;
import models.EMessages;
import models.dbentities.FAQModel;
import models.management.Manager;

/**
 * @author Jens N. Rammant
 *
 */
public class FAQManager extends Manager<FAQModel> {

	public static final int PAGESIZE = 15;
	
	public FAQManager() {
		super(new Finder<String,FAQModel>(String.class,FAQModel.class), PAGESIZE);
	}

	@Override
	public String[] getColumnHeaders() {
		String[] res = {"id",EMessages.get("faq.language"),EMessages.get("faq.name")};
		return res;
	}

	@Override
	public Call getListRoute(int page, String orderBy, String order,
			String filter) {
		return routes.FAQController.list(page, orderBy, order, filter);
	}

	@Override
	public Call getAddRoute() {
		return routes.FAQController.create();
	}

	@Override
	public Call getEditRoute(String id) {
		return routes.FAQController.edit(id);
	}

	@Override
	public Call getRemoveRoute(String id) {
		return routes.FAQController.remove(id);
	}

}
