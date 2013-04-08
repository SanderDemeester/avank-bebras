package models.question.questionset;

import controllers.question.routes;
import models.dbentities.QuestionSetQuestion;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

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
        super(QuestionSetQuestion.class, modelState);
        this.qsid = qsid;
        setFilterBy("difficulty");
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
        return null;
    }

    /**
     * Returns the path of the route that must be followed to save the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getSaveRoute() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the path of the route that must be followed to update(save) the current item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public play.api.mvc.Call getUpdateRoute() {
        throw new UnsupportedOperationException();
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
