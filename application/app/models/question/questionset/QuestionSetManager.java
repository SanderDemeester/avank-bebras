package models.question.questionset;

import com.avaje.ebean.Page;
import controllers.question.routes;
import models.dbentities.QuestionSetModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for the question set entity.
 *
 * @author Kevin Stobbelaar.
 */
public class QuestionSetManager extends Manager<QuestionSetModel> {

    private String contestid;
    private int questionsetid;

    /**
     * Constructor for manager class.
     *
     * @param state      model state
     */
    public QuestionSetManager(ModelState state, String contestid, int questionsetid) {
        super(QuestionSetModel.class, state, "grade", "grade");
        this.contestid = contestid;
        this.questionsetid = questionsetid;
    }

    /**
     * Returns a page with elements of type T.
     *
     * WARNING: it's better to override this method in your own manager!
     *
     * @param page     page number
     * @return the requested page
     */
    @Override
    public Page<QuestionSetModel> page(int page) {
        return  getFinder()
                .where()
                .ieq("contid", contestid)
                .ilike(filterBy, "%" + filter + "%")
                .orderBy(orderBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }

    /**
     * Returns the column headers for the objects of type T. This array must agree with
     * getFieldValues() from the ManageableModel
     *
     * @return column headers
     */
    public List<String> getColumnHeaders() {
        ArrayList<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add("name");
        columnHeaders.add("grade");
        columnHeaders.add("active");
        return columnHeaders;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page   current page number
     * @param orderBy current order by
     * @param order   current order
     * @param filter filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return controllers.competition.routes.CompetitionController.viewCompetition(contestid, page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getAddRoute() {
        return routes.QuestionSetController.create(contestid);
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        return routes.QuestionSetController.list(Integer.parseInt(id), 0, "qid", "asc", "");
    }

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getRemoveRoute(String id) {
        return controllers.competition.routes.CompetitionController.removeQuestionSet(Integer.parseInt(id), contestid);
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
        return routes.QuestionSetController.update(questionsetid);
    }

    /**
     * Returns the prefix for translation messages.
     *
     * @return name
     */
    @Override
    public String getMessagesPrefix() {
        return "question.questionset.manager";
    }
}
