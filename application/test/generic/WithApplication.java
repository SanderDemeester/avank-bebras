package generic;
import org.junit.After;

import play.test.FakeApplication;
import play.test.Helpers;

public class WithApplication {
    protected FakeApplication app;

    protected void start() {
        start(Helpers.fakeApplication());
    }

    protected void start(FakeApplication app) {
        this.app = app;
        Helpers.start(app);
    }

    @After
    public void stopPlay() {
        if (app != null) {
            Helpers.stop(app);
            app = null;
        }
    }

}
