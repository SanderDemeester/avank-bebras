package controllers;

import java.lang.Throwable;

import play.mvc.Http;
import play.mvc.Action;
import play.mvc.Result;


/**
 * The common header for the application pages
 * @author Ruben Taelman
 *
 */
public class CommonHeaders extends Action.Simple {

    @Override
    public Result call(Http.Context ctx) throws Throwable {
        ctx.response().setHeader("X-Frame-Options", "SAMEORIGIN");
        // This is because of the changes in pages when logged in or not:
        ctx.response().setHeader("Cache-Control", "no-cache");
        //ctx.response().setHeader(Http.HeaderNames.CACHE_CONTROL, "max-age=3600");
        return delegate.call(ctx);
    }

}
