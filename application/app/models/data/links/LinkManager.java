package models.data.links;

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

    @Override public Link createFromStrings(String... strings)
            throws CreationException {
        if(strings.length != 2) throw new CreationException(
                "Incorrect number of fields.",
                "manager.error.fieldno"
        );
        removed = strings;
        System.out.println('"' + strings[1] + '"');
        if("".equals(strings[0].trim()) || "".equals(strings[1].trim())) 
            throw new CreationException(
                "Empty fields.",
                "manager.error.empty"
        );
        return new Link(strings[0], strings[1]);
    }

}
