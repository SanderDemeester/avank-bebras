package models.data.manager;

import java.util.Arrays;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;

import models.data.manager.DataManager;
import models.data.manager.DataElement;
import models.data.links.LinkManager;
import models.data.Link;
import test.ContextTest;

/**
 * Uses the <tt>LinkManager</tt> as a representative
 * @see models.data.manager.DataManager
 * @see models.data.manager.DataElement
 * @see models.data.links.LinkManager
 * @see models.data.Link
 * @author Felix Van der Jeugt
 */
public class DataManagerTest extends ContextTest {

    private Link[] links;

    /** Just initializing some elements. */
    @Before public void makeSomeDataElements() {
        links = new Link[] {
            new Link("0", "/0"),
            new Link("1", "/1"),
            new Link("2", "/2")
        };
    }

    /** Just cleaning some elements. */
    @After public void deleteSomeDataElements() {
        links = null;
    }

    /** Test the creation of objects, which should succeed. */
    @Test public void testCreationWin() {
        DataManager<Link> dm = new LinkManager();
        Link link = null;
        try {
            link = dm.createFromStrings("3", "/3");
        } catch(DataManager.CreationException e) {
            Assert.fail("Failed to create a good string: " + e.getMessage());
        }
        Assert.assertEquals("3", link.getName());
        Assert.assertEquals("/3", link.getUrl());
    }

    /** Test the failing of creating wrong objects. */
    @Test public void testCreationFail() {
        DataManager<Link> dm = new LinkManager();
        Link link;
        boolean fail = true;
        try { link = dm.createFromStrings("3", ""); }
        catch(DataManager.CreationException e) { fail = false; }
        if(fail) Assert.fail("I could create an empty url");
        fail = true;

        try { link = dm.createFromStrings("", "/3"); }
        catch(DataManager.CreationException e) { fail = false; }
        if(fail) Assert.fail("I could create an empty name");
        fail = true;

        try { link = dm.createFromStrings("3"); }
        catch(DataManager.CreationException e) { fail = false; }
        if(fail) Assert.fail("I could create a link with a parameter short.");
        fail = true;

        try { link = dm.createFromStrings("3", "/3", "//3"); }
        catch(DataManager.CreationException e) { fail = false; }
        if(fail) Assert.fail("I could create a link with a parameter extra.");
        fail = true;
    }

    /** Test insertion of elements. */
    @Test public void testInsertion() {
        DataManager<Link> dm = new LinkManager();
        try {
            for(Link link : links) dm.add(link);
        } catch(DataManager.InsertionException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        String fail = "Not all elements were added.";
        Assert.assertTrue(fail, dm.list().containsAll(Arrays.asList(links)));
        Assert.assertTrue(fail, Arrays.asList(links).containsAll(dm.list()));
    }

    /** Test inserting and removing elements. */
    @Test public void testRemoval() {
        DataManager<Link> dm = new LinkManager();
        try {
            if(dm.list().size() == 0) for(Link link : links) dm.add(link);
            for(Link link : links) dm.remove(link.getName());
        } catch(DataManager.RemovalException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch(DataManager.InsertionException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        String fail = "Not all elements were removed.";
        Assert.assertTrue(fail, dm.list().size() == 0);
    }

}
