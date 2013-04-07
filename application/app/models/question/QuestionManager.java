package models.question;

import models.dbentities.QuestionModel;
import models.management.Manager;
import play.mvc.Call;
import controllers.question.routes;

public class QuestionManager extends Manager<QuestionModel>{

    public QuestionManager() {
        super(QuestionModel.class);
        this.setOrderBy("id");
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
        return routes.QuestionController.create();
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
        // TODO Auto-generated method stub
        return null;
    }

}
