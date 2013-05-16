/**
 *
 */
package controllers.faq;

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
        super(FAQModel.class,state, "id", "language");
    }

    @Override
    public Call getAddRoute() {
        return routes.FAQController.create();
    }

    @Override
    public Call getEditRoute(String id) {
        return routes.FAQController.edit(Integer.parseInt(id));
    }

    @Override
    public Call getRemoveRoute(String id) {
        return routes.FAQController.remove(Integer.parseInt(id));
    }

    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return routes.FAQController.list(page, orderBy, order, filter);
    }

    @Override
    public play.api.mvc.Call getSaveRoute() {
        //Using non-default form, so not necessary
        return null;
    }

    @Override
    public play.api.mvc.Call getUpdateRoute() {
        //Using non-default form, so not necessary
        return null;
    }

    @Override
    public String getMessagesPrefix() {
        return "faq";
    }





}
