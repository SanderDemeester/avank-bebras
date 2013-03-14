package models.question;

import com.avaje.ebean.Page;
import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 *
 * Test for the paging mechanism of the servers.
 *
 * @author Kevin Stobbelaar
 *
 */
public class ServerTest {

    @Test
    public void testPage() {
        running(fakeApplication(), new Runnable(){
            public void run(){
                // TODO write tests for pagination
            }
        });
    }
}
