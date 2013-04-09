package models.data.links;

import models.EMessages;
import models.data.Link;
import models.data.manager.DataManager;

/**
 * @author Felix van der Jeugt
 */
public class LinkManager extends DataManager<Link> {

    @Override public String[] columns() {
        return new String[]{ "Name", "URL" };
    }

    @Override public String url() {
        return "links";
    }

    @Override public String title() {
        return EMessages.get("links.title");
    }

    @Override public Class<Link> getTClass() {
        return Link.class;
    }

}
