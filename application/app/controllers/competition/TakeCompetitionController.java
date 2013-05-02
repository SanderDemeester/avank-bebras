package controllers.competition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.EMessages;
import models.competition.Competition;
import models.competition.CompetitionNotStartedException;
import models.competition.CompetitionType;
import models.competition.CompetitionUserStateManager;
import models.competition.TakeCompetitionManager;
import models.data.Language;
import models.data.Link;
import models.data.UnavailableLanguageException;
import models.data.UnknownLanguageCodeException;
import models.dbentities.CompetitionModel;
import models.dbentities.QuestionSetModel;
import models.management.ModelState;
import models.question.AnswerGeneratorException;
import models.question.QuestionFeedback;
import models.question.QuestionFeedbackGenerator;
import models.question.QuestionSet;
import models.user.AuthenticationManager;
import models.user.User;
import models.user.UserType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import controllers.EController;

/**
 * Controller for taking competitions. Includes the listing of available
 * competitions for each user type.
 *
 * @author Kevin Stobbelaar.
 */
public class TakeCompetitionController extends EController {

    /**
     * Checks if the current user has the given role.
     * @return true is the user has the given role
     */
    private static boolean userType(UserType type) {
        return AuthenticationManager.getInstance().getUser().getType().equals(type);
    }

    /**
     * Returns the default breadcrumbs for the contest pages.
     * @return breadcrumbs
     */
    private static List<Link> defaultBreadcrumbs(){
        List<Link> breadcrumbs = new ArrayList<Link>();
        breadcrumbs.add(new Link("Home", "/"));
        breadcrumbs.add(new Link(EMessages.get("competitions.breadcrumb"), "/available-contests"));
        return breadcrumbs;
    }

    /**
     * Returns a new competition manager object.
     * @return competition manager
     */
    private static TakeCompetitionManager getManager(){
        TakeCompetitionManager competitionManager = new TakeCompetitionManager(ModelState.READ, "name", null);
        competitionManager.setOrder("asc");
        competitionManager.setOrderBy("name");
        competitionManager.setFilter("");
        return competitionManager;
    }

    /**
     * Returns a page with a listing of available competitions
     * for the current type of user.
     * @return  available competitions list page
     */
    public static Result list(int page, String orderBy, String order, String filter){
        if (userType(UserType.ANON)){
            // anonymous user
            TakeCompetitionManager competitionManager = getManager();
            competitionManager.setExpressionList(competitionManager.getFinder()
                    .where()
                    .eq("type", CompetitionType.ANONYMOUS)
                    .eq("active", true)
                    .lt("starttime", new Date())
                    .gt("endtime", new Date())
            );
            Page<CompetitionModel> managerPage = competitionManager.page(page);
            return ok(views.html.competition.contests.render(defaultBreadcrumbs(), managerPage, competitionManager, order, orderBy, filter));
        }
        return TODO;
    }

    public static Result takeCompetition(String id){
        String stateID;
        
        CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
        Competition competition = new Competition(competitionModel);
        // TODO juiste question set kiezen !
        QuestionSet questionSet = competition.getQuestionSet(competition.getAvailableGrades().get(0));
        
        // TMP: start competition here
        CompetitionUserStateManager.getInstance().startCompetition(competition);
        
        // Register the user in the competition
        try {
            User user = AuthenticationManager.getInstance().getUser();
            if(user.isAnon()) {
                stateID = AuthenticationManager.getInstance().getAuthCookie();
                CompetitionUserStateManager.getInstance().registerAnon(
                        competition.getID(),
                        questionSet,
                        stateID
                    );
            } else {
                stateID = user.getID();
                CompetitionUserStateManager.getInstance().registerUser(
                            competition.getID(),
                            questionSet,
                            user
                        );
            }
        } catch (CompetitionNotStartedException e) {
            // TODO: prettify
            return badRequest(e.getMessage());
        }
        
        return ok(views.html.competition.run.questionSet.render(stateID, questionSet, null, defaultBreadcrumbs()));
    }
    
    /**
     * Submit competition answers
     * @param json answers in json format
     * @return message with the submission result
     */
    public static Result submit(String json) {
        JsonNode input = Json.parse(json);
        try {
            QuestionFeedback feedback = QuestionFeedbackGenerator.generateFromJson(
                    input, Language.getLanguage(EMessages.getLang()));
            
            // Save the results
            User user = AuthenticationManager.getInstance().getUser();
            if(user.isAnon()) {
                CompetitionUserStateManager.getInstance().getState(
                        feedback.getCompetitionID(),
                        AuthenticationManager.getInstance().getAuthCookie()
                    ).setResults(feedback);
            } else {
                CompetitionUserStateManager.getInstance().getState(
                        feedback.getCompetitionID(),
                        AuthenticationManager.getInstance().getUser().getID()
                    ).setResults(feedback);
            }
            
            
            // TMP: move this to the daemon and add a runnable when the competition is started
            CompetitionUserStateManager.getInstance().finishCompetition(feedback.getCompetitionID());
        } catch (UnavailableLanguageException
                | UnknownLanguageCodeException
                | AnswerGeneratorException
                | CompetitionNotStartedException e) {
            return badRequest(e.getMessage());
        }
        
        return ok("Submission was successful!");
    }
    
    /**
     * Submit competition answers for pupils that lost their connection
     * @param json answers in json format
     * @return message with the submission result
     */
    // TODO: add roles
    public static Result forceSubmit(String json) {
        // Dirty check, because Play is to dumb to throw exceptions
        try {
            new org.codehaus.jackson.JsonFactory().createJsonParser(json).nextToken();
        }
        catch(NullPointerException | IOException ex) { 
            return badRequest("Invalid answers.");
        }
        if(json == null || json.equals(""))
            return badRequest("Invalid answers.");
        try {
            JsonNode input = Json.parse(json);
            QuestionFeedback feedback = QuestionFeedbackGenerator.generateFromJson(
                    input, Language.getLanguage(EMessages.getLang()));
            // Save the results
            CompetitionUserStateManager.getInstance().getState(
                    feedback.getCompetitionID(),
                    feedback.getToken()
                ).setResults(feedback);
        } catch (UnavailableLanguageException
                | UnknownLanguageCodeException
                | AnswerGeneratorException
                | CompetitionNotStartedException e) {
            return badRequest(e.getMessage());
        }
        
        return ok("Submission was successful!");
    }
    
    /**
     * Submit competition answers and show feedback
     * @param json answers in json format
     * @return message with the submission result
     */
    public static Result feedback(String json) {
        // No saving is needed here, this is already done while submitting
        JsonNode input = Json.parse(json);
        QuestionFeedback feedback;
        try {
            feedback = QuestionFeedbackGenerator.generateFromJson(input, Language.getLanguage(EMessages.getLang()));
        } catch (UnavailableLanguageException
                | UnknownLanguageCodeException
                | AnswerGeneratorException e) {
            return badRequest(e.getMessage());
        }
        
        QuestionSetModel qsModel = Ebean.find(QuestionSetModel.class).where().idEq(feedback.getQuestionSetID()).findUnique();
        QuestionSet questionSet = new QuestionSet(qsModel);
        return ok(views.html.competition.run.questionSet.render("", questionSet, feedback, defaultBreadcrumbs()));
    }
    
    // TODO: add roles
    public static Result overview(String id) {
        // Make some error breadcrumbs for when an error occurs
        List<Link> errorBreadcrumbs = new ArrayList<Link>();
        errorBreadcrumbs.add(new Link("Home", "/"));
        errorBreadcrumbs.add(new Link("Error",""));
        
        CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
        if(competitionModel == null) return internalServerError(views.html.commons.error.render(errorBreadcrumbs, EMessages.get("error.title"), EMessages.get("error.text")));
        Competition competition = new Competition(competitionModel);
        
        return ok(views.html.competition.run.overview.render(competition, defaultBreadcrumbs()));
    }
    
    // TODO: add roles
    public static Result overviewData(String id) {
        try {
            ObjectNode result = Json.newObject();
            result.put("amountFinished", CompetitionUserStateManager.getInstance().getAmountFinished(id));
            result.put("amountRegistered", CompetitionUserStateManager.getInstance().getAmountRegistered(id));
            return ok(result);
        } catch (CompetitionNotStartedException e) {
            return badRequest(e.getMessage());
        }
        
    }
    
    // TODO: add roles
    public static Result forceFinish(String id) {
        try {
            CompetitionUserStateManager.getInstance().finishCompetition(id);
            return ok("The competition has been finished.");
        } catch (CompetitionNotStartedException e) {
            return badRequest(e.getMessage());
        }
    }

}
