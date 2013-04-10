package models.data.links;

import models.EMessages;
import models.data.Link;
import models.data.manager.DataManager;

/**
 * @author Felix van der Jeugt
 */
public class LinkManager extends DataManager<Link> {

    @Override public String[] columns() {
        return new String[]{ "forms.name", "links.url" };
    }

    @Override public String url() {
        return "links";
    }

    @Override public String title() {
        return "links.title";
    }

    @Override public Class<Link> getTClass() {
        return Link.class;
    }

    @Override public Link createFromStrings(String... strings) {
        return new Link(strings[0], strings[1]);
    }

}
