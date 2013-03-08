package controllers;

import play.mvc.Controller;

public class EController extends Controller{
	public static void setCommonHeaders() {
		response().setHeader("X-Frame-Options", "SAMEORIGIN");
		response().setHeader(CACHE_CONTROL, "max-age=3600");
	}
}
