package controllers.statistics;

import java.util.List;
import java.util.ArrayList;

import javax.validation.Valid;

import com.avaje.ebean.Ebean;

import play.mvc.Result;

import controllers.EController;

import models.dbentities.UserModel;
import models.data.Link;
import models.user.AuthenticationManager;
import models.statistics.Population;
import models.statistics.SinglePopulation;
import models.statistics.PopulationFactory;
import models.statistics.PopulationChooser;
import models.statistics.PopulationFactoryException;

import views.html.statistics.statistics;

/**
 * This class returns the statistics page for the logged in user.
 * @author Felix Van der Jeugt
 */
public class Statistics extends EController {

    /* Our lovely breadcrumbs. */
    private static List<Link> breadcrumbs = new ArrayList<Link>();
    static {
        breadcrumbs.add(new Link("app.home", "/"));
        breadcrumbs.add(new Link("statistics.title", "/statistics"));
    }

    /** Display the main statistics page. */
    public static Result show() {

        // Open the population chooser for the logged in user.
        PopulationChooser populationChooser =
            models.statistics.Statistics.getPopulationChooser(
                    AuthenticationManager.getInstance().getUser()
            );

        // Derive the selected populations from the POST data.
        GroupForm gf = form(GroupForm.class).bindFromRequest().get();
        List<Population> populations = new ArrayList<Population>();
        if(gf.colours != null)
        for(int i=0; i < gf.colours.size(); i++) {
            try {
                populations.add(PopulationFactory.instance().create(
                    gf.types.get(i),
                    gf.ids.get(i),
                    gf.colours.get(i)
                ));
            } catch(PopulationFactoryException e) {
                // TODO Show error message.
            }
        }

        // Make sure the user doesn't view populations he shouldn't, and disable
        // those he already selected.
        populations = populationChooser.filter(populations);

        return ok(statistics.render(populations, populationChooser, breadcrumbs));
    }

    public static class GroupForm {
        @Valid public List<String> colours;
        @Valid public List<String> types;
        @Valid public List<String> ids;
        public GroupForm() {}
    }

}
