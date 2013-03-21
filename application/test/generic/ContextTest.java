package generic;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;

import play.test.FakeApplication;
import play.test.Helpers;

import play.mvc.Content;
import play.mvc.Http;

import play.i18n.Lang;

/**
 * If you want to run your class as if the application was actually running,
 * this may be interesting.
 * @author Felix Van der Jeugt
 */
public class ContextTest {

    /**
     * Set up the application.
     */
    @BeforeClass public static void setupApplication() {
        Helpers.start(Helpers.fakeApplication(Helpers.inMemoryDatabase()));
    }

    /**
     * Set up the context.
     */
    @Before public void setupContext() {
        Http.Context.current = new ThreadLocal<Http.Context>() {
            @Override protected Http.Context initialValue() {
                return makeContext();
            }
        };
    }

    protected Http.Context makeContext() {
        return new Http.Context(
            makeRequest(),
            makeSessionData(),
            makeFlashData()
        );
    }

    protected Map<String, String> makeSessionData() {
        return new HashMap<String, String>();
    }

    protected Map<String, String> makeFlashData() {
        return new HashMap<String, String>();
    }

    protected Http.Request makeRequest() {
        return new StubRequest() {
            @Override public List<Lang> acceptLanguages() {
                List<Lang> l = new ArrayList<Lang>();
                l.add(Lang.forCode("en-US"));
                return l;
            }
        };
    }

    protected void runInApplication(Runnable block) {
        Helpers.running(
            Helpers.fakeApplication(Helpers.inMemoryDatabase()),
            block
        );
    }

    /**
     * Breaks down the application.
     */
    @AfterClass public static void breakdownApplication() {
        Helpers.stop(Helpers.fakeApplication(Helpers.inMemoryDatabase()));
    }

    protected class StubRequest extends Http.Request {

        @Override public Http.RequestBody body() { return null; }
        @Override public List<String> accept() { return null; }
        @Override public List<Lang> acceptLanguages() { return null; }
        @Override public boolean accepts(String mediaType) { return true; }
        @Override public Http.Cookies cookies() { return null; }
        @Override public Map<String,String[]> headers() { return null; }
        @Override public String host() { return null; }
        @Override public String method() { return null; }
        @Override public String path() { return null; }
        @Override public Map<String,String[]> queryString() { return null; }
        @Override public String remoteAddress() { return null; }
        @Override public String uri() { return null; }

    }

}
