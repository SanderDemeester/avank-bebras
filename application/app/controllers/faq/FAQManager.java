/**
 * 
 */
package controllers.faq;

import play.db.ebean.Model.Finder;
import play.mvc.Call;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getListRoute(int page, String orderBy, String order,
			String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getAddRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getEditRoute(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Call getRemoveRoute(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
