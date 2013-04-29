package controllers.statistics;

import java.util.List;
import java.util.ArrayList;

import javax.validation.Valid;

import com.avaje.ebean.Ebean;

import play.mvc.Result;

import controllers.EController;

import models.dbentities.UserModel;
import models.data.Link;
import models.statistics.Colour;
import models.statistics.PopulationGroup;
import models.statistics.SinglePopulation;
import models.statistics.PopulationFactory;
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
        GroupForm gf = form(GroupForm.class).bindFromRequest().get();
        List<PopulationGroup> groups = new ArrayList<PopulationGroup>();

        if(gf.colournames != null)
        for(int i=0; i < gf.colournames.size(); i++) {
            PopulationGroup pg = new PopulationGroup();
            pg.colour = new Colour(gf.colournames.get(i),
                gf.colourhtmls.get(i));

            if(gf.types.get(i) != null)
            for(int j=0; j < gf.types.get(i).size(); j++) {
                try {
                    pg.populations.add(PopulationFactory.instance().create(
                        gf.types.get(i).get(j), gf.ids.get(i).get(j)));
                } catch(PopulationFactoryException e) {
                    // TODO Show error message.
                }
            }
            groups.add(pg);
        }
        System.out.println("YYYAAAAAY, groups");
        // TODO remove this when there are actual values.
        if(groups.size() == 0) {
            PopulationGroup pg = new PopulationGroup(Colour.RED);
            try {
            pg.populations.add(PopulationFactory.instance().create(
                "INDIVIDUAL", "fvdjeugt"));
            } catch(PopulationFactoryException e) {
                System.out.println("DAMMIT");
            }
            groups.add(pg);
        } else {
            System.out.println("YYYAAAAAY, groups");
        }
        return ok(statistics.render(groups, breadcrumbs));
    }

    public static class GroupForm {
        @Valid public List<String> colournames;
        @Valid public List<String> colourhtmls;
        @Valid public List<List<String>> types;
        @Valid public List<List<String>> ids;
        public GroupForm() {}
    }

}
