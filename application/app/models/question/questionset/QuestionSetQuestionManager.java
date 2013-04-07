package models.question.questionset;

import com.avaje.ebean.Page;
import controllers.question.routes;
import models.dbentities.QuestionSetQuestion;
import models.management.Manageable;
import models.management.Manager;
import play.db.ebean.Model;
import play.mvc.Call;

/**
 * Manager for question set questions entity.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetQuestionManager extends Manager {

    private String qsid;

    /**
     * Constructor for manager.
     *
     * @param pageSize number of elements displayed on one page
     * @param qsid question set id
     */
    public QuestionSetQuestionManager(int pageSize, String qsid) {
        super(new Model.Finder<String, QuestionSetQuestion>(String.class, QuestionSetQuestion.class), pageSize);
        this.qsid = qsid;
    }

    /**
     * Returns a page with elements of type T.
     *
     * WARNING: it's better to override this method in your own manager!
     *
     * @param page     page number
     * @param orderBy  attribute to sort on
     * @param order    sort order
     * @param filter   filter to select specific elements
     * @return the requested page
     */
    public Page<Manageable> page(int page, String orderBy, String order, String filter) {
        return (Page<Manageable>) getFinder()
                .where().ieq("qsid", qsid)
                .orderBy(orderBy + " " + order)
                .findPagingList(getPageSize())
                .getPage(page);
    }

    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    @Override
    public String[] getColumnHeaders() {
        String[] result = {"qsid", "qid", "difficulty"};
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
        return routes.QuestionSetController.list(qsid, page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getAddRoute() {
        return routes.QuestionSetController.addQuestion(qsid);
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        throw new UnsupportedOperationException();
    }
}
