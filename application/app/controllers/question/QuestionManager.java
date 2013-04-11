package controllers.question;

import models.dbentities.QuestionModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;
import controllers.question.routes;

public class QuestionManager extends Manager<QuestionModel>{
    private String id;

    public QuestionManager(String id, ModelState state) {
        this(state);
        this.id = id;
    }
    
    public QuestionManager(ModelState state) {
        super(QuestionModel.class, state, "id", "officialid");
    }

    @Override
    public String[] getColumnHeaders() {
        String[] columnHeaders = {"Official ID", "Server", "Path", "Active", "Author"};
        return columnHeaders;
    }

    @Override
    public Call getListRoute(int page, String filter) {
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
