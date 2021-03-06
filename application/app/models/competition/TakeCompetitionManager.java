package models.competition;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import controllers.competition.routes;
import models.dbentities.CompetitionModel;
import models.management.Manager;
import models.management.ModelState;
import play.mvc.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for available competitions for each type of user.
 *
 * @author Kevin Stobbelaar.
 */
public class TakeCompetitionManager extends Manager<CompetitionModel> {

    private String contestid;
    private ExpressionList<CompetitionModel> expressionList;

    /**
     * Constructor for manager class.
     *
     * @param contestid  contest id
     * @param state      model state
     * @param orderBy    column to be ordered on
     */
    public TakeCompetitionManager(ModelState state, String orderBy, String contestid) {
        super(CompetitionModel.class, state, orderBy, "name");
        this.contestid = contestid;
        setPageSize(5);
        this.expressionList = getFinder().where();
    }

    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    @Override
    public List<String> getColumnHeaders() {
        ArrayList<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add("name");
        columnHeaders.add("type");
        columnHeaders.add("active");
        columnHeaders.add("starttime");
        columnHeaders.add("endtime");
        columnHeaders.add("creator");
        columnHeaders.add("duration");
        return columnHeaders;
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
    public Page<CompetitionModel> page(int page) {
        return getDataSet()
                .ilike(filterBy, "%" + filter + "%")
                .orderBy(orderBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }

    /**
     * Returns the expression list for quering the database.
     * @return expression list for quering
     */
    @Override
    protected ExpressionList<CompetitionModel> getDataSet(){
        return expressionList;
    }

    /**
     * Set expression list for quering the database.
     * @param expressionList list for quering
     */
    public void setExpressionList(ExpressionList<CompetitionModel> expressionList){
        this.expressionList = expressionList;
    }

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page    current page number
     * @param orderBy current order by
     * @param order   current order
     * @param filter  filter on the items
     * @return Call Route that must be followed
     */
    @Override
    public Call getListRoute(int page, String orderBy, String order, String filter) {
        return routes.TakeCompetitionController.list(page, orderBy, order, filter);
    }

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    @Override
    public Call getEditRoute(String id) {
        CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
        if (new Competition(competitionModel).getType() != CompetitionType.RESTRICTED){
            // the user has to select his preferred grade
            return routes.TakeCompetitionController.chooseGrade(id);
        }
        return routes.TakeCompetitionController.takeCompetition(id);
    }

    /**
     * Returns the prefix for translation messages.
     *
     * @return name
     */
    @Override
    public String getMessagesPrefix() {
        return "competition.manager";
    }
}
