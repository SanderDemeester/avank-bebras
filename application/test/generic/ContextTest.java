package generic;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.lang.UnsupportedOperationException;

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

    /**
     * Default context creation, override to customize.
     */
    protected Http.Context makeContext() {
        return new Http.Context(
            makeRequest(),
            makeSessionData(),
            makeFlashData()
        );
    }

    /**
     * Default Session creation. You can override this class and add this to the
     * session.
     * @return A Map mapping the Session keys on their values.
     */
    protected Map<String, String> makeSessionData() {
        return new HashMap<String, String>();
    }

    /**
     * Default FlashData creation.
     * @return The FlashData for in the context.
     */
    protected Map<String, String> makeFlashData() {
        return new HashMap<String, String>();
    }

    /**
     * Default Request stub. Override to implement request functions, and thus
     * mimic the kind of user request you want.
     * @return The Request for the Context.
     */
    protected Http.Request makeRequest() {
        return new StubRequest();
    }

    /**
     * Runnable code blocks passed to this method will run in the application
     * with a proper Context.
     */
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

        @Override public Http.RequestBody body() {
            throw new UnsupportedOperationException();
        }

        @Override public List<String> accept() {
            throw new UnsupportedOperationException();
        }

        @Override public List<Lang> acceptLanguages() {
            throw new UnsupportedOperationException();
        }

        @Override public boolean accepts(String mediaType) {
            throw new UnsupportedOperationException();
        }

        @Override public Http.Cookies cookies() {
            throw new UnsupportedOperationException();
        }

        @Override public Map<String,String[]> headers() {
            throw new UnsupportedOperationException();
        }

        @Override public String host() {
            throw new UnsupportedOperationException();
        }

        @Override public String method() {
            throw new UnsupportedOperationException();
        }

        @Override public String path() {
            throw new UnsupportedOperationException();
        }

        @Override public Map<String,String[]> queryString() {
            throw new UnsupportedOperationException();
        }

        @Override public String remoteAddress() {
            throw new UnsupportedOperationException();
        }

        @Override public String uri() {
            throw new UnsupportedOperationException();
        }

    }

}
