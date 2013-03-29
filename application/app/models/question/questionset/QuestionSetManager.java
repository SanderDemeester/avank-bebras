package models.question.questionset;

import controllers.question.questionset.routes;
import models.dbentities.QuestionSetModel;
import models.management.Manager;
import play.db.ebean.Model;
import play.mvc.Call;

/**
 * Manager for the question sets.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetManager extends Manager<QuestionSetModel> {

    public QuestionSetManager(){
        super(new Model.Finder<String, QuestionSetModel>(String.class, QuestionSetModel.class), 6);
    }

    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    @Override
    public String[] getColumnHeaders() {
        String[] result = {"id"};
        return result;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page    current page number
     * @param orderBy name of the column to sort on
     * @param order   ASC or DESC
     * @param filter  filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return routes.QuestionSetController.overview("", page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getAddRoute() {
        return routes.QuestionSetController.create();
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        return routes.QuestionSetController.edit(id);
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        return routes.QuestionSetController.remove(id);
    }
}
