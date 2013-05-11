package controllers.question;

import models.dbentities.QuestionModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;
import controllers.question.routes;

/**
 * Manage questions
 * @author Ruben Taelman
 *
 */
public class QuestionManager extends Manager<QuestionModel>{
    private String id;

    /**
     * Create a new QuestionManager based
     * @param id the id for the requested question, only used when editing a question
     * @param state the state the manager should be in
     */
    public QuestionManager(String id, ModelState state) {
        this(state);
        this.id = id;
    }
    
    /**
     * Create a new QuestionManager based
     * @param state the state the manager should be in
     */
    public QuestionManager(ModelState state) {
        super(QuestionModel.class, state, "id", "officialid");
    }

    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return routes.QuestionController.list(page, orderBy, order, filter);
    }

    @Override
    public Call getAddRoute() {
        // Not really an "add" page, but more an approval page for semi-created questions
        return routes.QuestionController.listSubmits(0, "");
    }

    @Override
    public Call getEditRoute(String id) {
        return routes.QuestionController.edit(id);
    }

    @Override
    public Call getRemoveRoute(String id) {
        return routes.QuestionController.remove(id);
    }

    @Override
    public play.api.mvc.Call getSaveRoute() {
        return routes.QuestionController.save();
    }

    @Override
    public String getMessagesPrefix() {
        return "questions";
    }

    @Override
    public play.api.mvc.Call getUpdateRoute() {
        return routes.QuestionController.update(id);
    }

}
