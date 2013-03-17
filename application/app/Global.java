


import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;

import java.util.ArrayList;

import models.data.Link;
import play.GlobalSettings;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

/**
 * @author Sander Demeester
 */
public class Global extends GlobalSettings{

    public Result onError(Throwable t) {
        return internalServerError(
            views.html.index.render("fout", new ArrayList<Link>())
        );
    }


    @Override
    public Result onHandlerNotFound(RequestHeader request){
        return notFound(
            views.html.PageNotFound.render("These aren't the pages you're looking for", request.path())
        );
    }
}

