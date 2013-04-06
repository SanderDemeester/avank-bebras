package models.question;

import models.dbentities.QuestionModel;
import models.management.Manager;
import play.db.ebean.Model.Finder;
import play.mvc.Call;
import controllers.question.routes;

public class QuestionManager extends Manager<QuestionModel>{

    public QuestionManager() {
        super(new Finder<String, QuestionModel>(String.class, QuestionModel.class), Manager.DEFAULTPAGESIZE);
    }

    @Override
    public String[] getColumnHeaders() {
        String[] columnHeaders = {"Official ID", "Server", "Path", "Active", "Author"};
        return columnHeaders;
    }

    @Override
    public Call getListRoute(int page, String orderBy, String order,
            String filter) {
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
    public String getUniqueField() {
        return "id";
    }

}
