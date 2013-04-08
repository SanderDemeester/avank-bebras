/**
 * 
 */
package controllers.faq;

import models.EMessages;
import models.dbentities.FAQModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

/**
 * @author Jens N. Rammant
 *
 */
public class FAQManager extends Manager<FAQModel> {

	public static final int PAGESIZE = 15;
	
	public FAQManager(ModelState state) {
		super(FAQModel.class, state, "id", "id");
	}

	@Override
	public String[] getColumnHeaders() {
		String[] res = {"id",EMessages.get("faq.language"),EMessages.get("faq.name")};
		return res;
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

    @Override
    public Call getListRoute(int page, String filter) {
        return routes.FAQController.list(page, orderBy, order, filter);
    }

    @Override
    public play.api.mvc.Call getSaveRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public play.api.mvc.Call getUpdateRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMessagesPrefix() {
        // TODO Auto-generated method stub
        return null;
    }

}
