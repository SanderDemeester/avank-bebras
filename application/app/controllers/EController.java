package controllers;

import play.mvc.Controller;
import play.mvc.With;

import controllers.CommonHeaders;

/**
 * @author Ruben Taelman
 */
@With(CommonHeaders.class)
public class EController extends Controller{}
