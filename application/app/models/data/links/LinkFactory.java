package models.data.links;

import models.data.Link;
import models.data.manager.DataFactory;

/**
 * @author Felix Van der Jeugt
 * @see models.data.manager.DataFactory
 */
public class LinkFactory implements DataFactory<Link> {

    @Override public int stringsExpected() {
        return 2;
    }

    @Override public Link createFromStrings(String... strings) {
        return new Link(strings[0], strings[1]);
    }

}
