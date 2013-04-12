package models.question.questionset;

import com.avaje.ebean.Page;
import controllers.question.routes;
import models.dbentities.QuestionSetQuestion;
import models.management.ManageableModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for question set questions entity.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetQuestionManager extends Manager<QuestionSetQuestion> {

    private String qsid;

    /**
     * Constructor for manager.
     *
     * @param qsid question set id
     */
    public QuestionSetQuestionManager(ModelState modelState, String qsid) {
        super(QuestionSetQuestion.class, modelState, "difficulty", "difficulty");
        this.qsid = qsid;
    }

    /**
     * Returns a page with elements of type T.
     *
     * WARNING: it's better to override this method in your own manager!
     *
     * @param page     page number
     * @return the requested page
     */
    public Page<QuestionSetQuestion> page(int page) {
        return  getFinder()
                .where()
                .ieq("qsid", qsid)
                .ilike(filterBy, "%" + filter + "%")
                .orderBy(orderBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }


    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    @Override
    public List<String> getColumnHeaders() {
        ArrayList<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add("qid");
        columnHeaders.add("qsid");
        columnHeaders.add("difficulty");
        return columnHeaders;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page   current page number
     * @param filter filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String filter) {
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
        return null;
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        return routes.QuestionSetController.removeQuestion(qsid, id);
    }

    /**
     * Returns the path of the route that must be followed to save the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getSaveRoute() {
        return null;
    }

    /**
     * Returns the path of the route that must be followed to update(save) the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getUpdateRoute() {
        return null;
    }

    /**
     * Returns the prefix for translation messages.
     *
     * @return name
     */
    @Override
    public String getMessagesPrefix() {
        return "question.questionset";
    }
}
