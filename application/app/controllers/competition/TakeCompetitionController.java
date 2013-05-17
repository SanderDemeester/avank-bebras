package controllers.competition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.EMessages;
import models.competition.Competition;
import models.competition.CompetitionNotStartedException;
import models.competition.CompetitionType;
import models.competition.CompetitionUserState;
import models.competition.CompetitionUserStateManager;
import models.competition.TakeCompetitionManager;
import models.data.Grade;
import models.data.Language;
import models.data.Link;
import models.data.UnavailableLanguageException;
import models.data.UnknownLanguageCodeException;
import models.dbentities.ClassGroup;
import models.dbentities.CompetitionModel;
import models.dbentities.ContestClass;
import models.dbentities.QuestionSetModel;
import models.dbentities.UserModel;
import models.management.ModelState;
import models.question.AnswerGeneratorException;
import models.question.QuestionFeedback;
import models.question.QuestionFeedbackGenerator;
import models.question.QuestionSet;
import models.user.AuthenticationManager;
import models.user.Role;
import models.user.User;
import models.user.UserType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.data.Form;
import play.libs.Json;
import play.mvc.Result;
import views.html.commons.noaccess;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
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
     * Check if the current user is authorized for the competition management
     * @return is the user authorized
     */
    private static boolean isAuthorized() {
        return AuthenticationManager.getInstance().getUser().hasRole(Role.MANAGECONTESTS);
    }

    /**
     * Returns a list with running competition ids
     * @return running competition ids
     */
    private static List<String> getRunningCompetitions(){
        List<CompetitionModel> competitionModels = Ebean.find(CompetitionModel.class)
                    .where()
                    .lt("starttime", new Date())
                    .gt("endtime", new Date())
                    .eq("active", true)
                    .findList();
        List<String> ids = new ArrayList<String>();
        for (CompetitionModel competitionModel : competitionModels){
            if (checkSupportedLanguages(competitionModel)){
                ids.add(competitionModel.id);
            }
        }
        return ids;
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
    private static TakeCompetitionManager getManager(String orderBy, String order, String filter){
        TakeCompetitionManager competitionManager = new TakeCompetitionManager(ModelState.READ, "name", null);
        competitionManager.setOrder(order);
        competitionManager.setOrderBy(orderBy);
        competitionManager.setFilter(filter);
        return competitionManager;
    }

    /**
     * Returns a page with a listing of available competitions
     * for the current type of user.
     * @param page page nr
     * @param orderBy order field
     * @param order ordering
     * @param filter filter
     * @return  available competitions list page
     */
    public static Result list(int page, String orderBy, String order, String filter){
        TakeCompetitionManager competitionManager = getManager(orderBy, order, filter);
        if (userType(UserType.ANON)){
            // anonymous user can only see "running" anonymous competitions
            List<String> competitionIds = new ArrayList<String>();
            for (CompetitionModel competitionModel : Ebean.find(CompetitionModel.class).findList()){
                if (checkSupportedLanguages(competitionModel)){
                    competitionIds.add(competitionModel.id);
                }
            }
            List<String> ids = getRunningCompetitions();
            competitionManager.setExpressionList(competitionManager.getFinder()
                    .where()
                    .eq("type", CompetitionType.ANONYMOUS.name())
                    .in("id", competitionIds)
                    .in("id", ids)
            );
            Page<CompetitionModel> managerPage = competitionManager.page(page);
            return ok(views.html.competition.contests.render(defaultBreadcrumbs(), managerPage, competitionManager, order, orderBy, filter));
        }
        if (userType(UserType.PUPIL_OR_INDEP)){
            // pupils can see "running" anonymous and unrestricted competitions + restricted competitions if their class is registered
            UserModel user = AuthenticationManager.getInstance().getUser().getData();
            ClassGroup classGroup = Ebean.find(ClassGroup.class).where().idEq(user.classgroup).findUnique();
            List<ContestClass> contestClasses = Ebean.find(ContestClass.class).where().eq("classid", classGroup).findList();
            List<String> competitionIds = new ArrayList<String>();
            for (ContestClass contestClass : contestClasses){
                // check if this contest contains a question set that supports the current user's language
                if (checkSupportedLanguages(contestClass.contestid)){
                    competitionIds.add(contestClass.contestid.id);
                }
            }
            List<String> ids = getRunningCompetitions();
            competitionManager.setExpressionList(competitionManager.getFinder()
                    .where()
                    .or(
                            Expr.or(
                                    Expr.eq("type", CompetitionType.ANONYMOUS.name()),
                                    Expr.eq("type", CompetitionType.UNRESTRICTED.name())
                            ),
                            Expr.and(
                                    Expr.eq("type", CompetitionType.RESTRICTED.name()),
                                    Expr.in("id", competitionIds)
                                    ))
                    .in("id", ids)
            );
            Page<CompetitionModel> managerPage = competitionManager.page(page);
            return ok(views.html.competition.contests.render(defaultBreadcrumbs(), managerPage, competitionManager, order, orderBy, filter));
        }
        // teachers, organizers and admins will be taken to the competition management index page
        return redirect(routes.CompetitionController.index(0, "name", "asc", ""));
    }

    /**
     * Returns the page on which the user can choose the preferred grade for the a selected contest.
     * This is needed to be able to pick to correct question set according to the user's grade.
     * @param id contest id.
     * @return choose-grade page
     */
    public static Result chooseGrade(String id){
        Form<Grade> form = form(Grade.class).bindFromRequest();
        List<Link> breadcrumbs = defaultBreadcrumbs();
        breadcrumbs.add(new Link(EMessages.get("competitions.grade.breadcrumb"), "/available-contests/" + id + "/grade"));
        CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
        return ok(views.html.competition.grade.render(breadcrumbs, form, new Competition(competitionModel)));
    }

    /**
     * Setting up the competition to be taken.
     * Returns the page on which users can start the competition and answer questions.
     * @param id contest id
     * @return start-contest page
     */
    public static Result takeCompetition(String id){
        String stateID;

        CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
        Competition competition = new Competition(competitionModel);
        User user = AuthenticationManager.getInstance().getUser();

        // setting the correct grade
        Grade grade;
        if (user.data != null && user.data.classgroup != null && competitionModel.type == CompetitionType.RESTRICTED){
            ClassGroup classGroup = Ebean.find(ClassGroup.class).where().idEq(user.data.classgroup).findUnique();
            grade = Ebean.find(Grade.class).where().ieq("name", classGroup.level).findUnique();
        }
        else {
            String gradeName = form(Grade.class).bindFromRequest().field("grade").value();
            grade = Ebean.find(Grade.class).where().ieq("name", gradeName).findUnique();
        }

        QuestionSet questionSet = competition.getQuestionSet(grade);

        if (questionSet == null){
            // can happen when the competition does not contain a question set with the user's grade
            return ok(views.html.commons.error.render(defaultBreadcrumbs(),
                    EMessages.get("competition.questionset.title"), EMessages.get("competition.questionset.info"))
            );
        }

        if (userType(UserType.ANON)){
            CompetitionUserStateManager.getInstance().startCompetition(competition);
        }

        // Register the user in the competition
        try {
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
            return ok(views.html.commons.error.render(defaultBreadcrumbs(),
                    EMessages.get("competition.started.title"), EMessages.get("competition.started.info"))
            );
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
            CompetitionUserState state = null;
            if(AuthenticationManager.getInstance().getUser().isAnon()) {
                state = CompetitionUserStateManager.getInstance().getState(
                        feedback.getCompetitionID(),
                        AuthenticationManager.getInstance().getAuthCookie()
                    );
            } else {
                state = CompetitionUserStateManager.getInstance().getState(
                        feedback.getCompetitionID(),
                        AuthenticationManager.getInstance().getUser().getID()
                    );
            }
            if(state == null) return badRequest(EMessages.get("competition.run.submit.invalidUserSelf"));
            state.setResults(feedback);
        } catch (CompetitionNotStartedException e) {
            return badRequest(EMessages.get("competition.run.submit.notStarted"));
        } catch (UnavailableLanguageException
                | UnknownLanguageCodeException
                | AnswerGeneratorException e) {
            return badRequest(e.getMessage());
        }

        return ok(EMessages.get("competition.run.submit.ok"));
    }

    /**
     * Submit competition answers for pupils that lost their connection
     * @param json answers in json format
     * @return message with the submission result
     */
    public static Result forceSubmit(String json) {
        if(isAuthorized()) {
            // Dirty check, because Play is to dumb to throw exceptions
            try {
                new org.codehaus.jackson.JsonFactory().createJsonParser(json).nextToken();
            }
            catch(NullPointerException | IOException ex) {
                return badRequest(EMessages.get("competition.run.submit.invalid"));
            }
            if(json == null || json.equals(""))
                return badRequest(EMessages.get("competition.run.submit.invalid"));
            try {
                JsonNode input = Json.parse(json);
                QuestionFeedback feedback = QuestionFeedbackGenerator.generateFromJson(
                        input);
                // Save the results
                CompetitionUserState state = null;
                state = CompetitionUserStateManager.getInstance().getState(
                        feedback.getCompetitionID(),
                        feedback.getToken()
                    );
                if(state == null) return badRequest(EMessages.get("competition.run.submit.invalidUser"));
                state.setResults(feedback);
            } catch (AnswerGeneratorException
                    | CompetitionNotStartedException e) {
                return badRequest(e.getMessage());
            }

            return ok(EMessages.get("competition.run.submit.ok"));
        } else return forbidden();
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

    /**
     * Live competition overview page
     * @param id competition id
     * @return view of the competition overview
     */
    public static Result overview(String id) {
        // Make some error breadcrumbs for when an error occurs
        List<Link> errorBreadcrumbs = new ArrayList<Link>();
        errorBreadcrumbs.add(new Link("Home", "/"));
        errorBreadcrumbs.add(new Link("Error",""));

        if(isAuthorized()) {
            CompetitionModel competitionModel = Ebean.find(CompetitionModel.class).where().idEq(id).findUnique();
            if(competitionModel == null) return internalServerError(views.html.commons.error.render(errorBreadcrumbs, EMessages.get("error.title"), EMessages.get("error.text")));
            Competition competition = new Competition(competitionModel);

            return ok(views.html.competition.run.overview.render(competition, defaultBreadcrumbs()));
        } else return ok(noaccess.render(errorBreadcrumbs));
    }

    /**
     * Live competition overview data
     * @param id competition id
     * @return json encoded data
     */
    public static Result overviewData(String id) {
        // Make some error breadcrumbs for when an error occurs
        List<Link> errorBreadcrumbs = new ArrayList<Link>();
        errorBreadcrumbs.add(new Link("Home", "/"));
        errorBreadcrumbs.add(new Link("Error",""));

        if(isAuthorized()) {
            try {
                ObjectNode result = Json.newObject();
                result.put("amountFinished", CompetitionUserStateManager.getInstance().getAmountFinished(id));
                result.put("amountRegistered", CompetitionUserStateManager.getInstance().getAmountRegistered(id));
                return ok(result);
            } catch (CompetitionNotStartedException e) {
                return badRequest(e.getMessage());
            }
        } else return ok(noaccess.render(errorBreadcrumbs));

    }

    /**
     * Force a competition to finish before the expiration date
     * @param id competitionid
     * @return return message
     */
    public static Result forceFinish(String id) {
        if(isAuthorized()) {
            try {
                CompetitionUserStateManager.getInstance().finishCompetition(id);
                return ok(EMessages.get("competition.run.finished"));
            } catch (CompetitionNotStartedException e) {
                return badRequest(e.getMessage());
            }
        } else return forbidden();
    }

    /**
     * Returns a snippet of available contests.
     * @return available contests snippet.
     */
    public static List<CompetitionModel> snippet(){
        UserModel user = AuthenticationManager.getInstance().getUser().getData();
        ClassGroup classGroup = Ebean.find(ClassGroup.class).where().idEq(user.classgroup).findUnique();
        List<ContestClass> contestClasses = Ebean.find(ContestClass.class).where().eq("classid", classGroup).findList();
        List<String> competitionIds = new ArrayList<String>();
        for (ContestClass contestClass : contestClasses){
            if (checkSupportedLanguages(contestClass.contestid)){
                competitionIds.add(contestClass.contestid.id);
            }
        }
        TakeCompetitionManager competitionManager = getManager("name", "asc", "");
        List<String> ids = getRunningCompetitions();
        List<CompetitionModel> competitionModels = competitionManager.getFinder()
                .where()
                .or(
                        Expr.or(
                                Expr.eq("type", CompetitionType.ANONYMOUS.name()),
                                Expr.eq("type", CompetitionType.UNRESTRICTED.name())
                        ),
                        Expr.and(
                                Expr.eq("type", CompetitionType.RESTRICTED.name()),
                                Expr.in("id", competitionIds)
                        ))
                .in("id", ids)
                .findList()
        ;

        if (competitionModels.size() > 5){
            competitionModels = competitionModels.subList(0,4);
        }
        return competitionModels;
    }

    /**
     * Returns a snippet of available anonymous contests.
     * @return
     */
    public static List<CompetitionModel> anonymousSnippet(){
        TakeCompetitionManager competitionManager = getManager("name", "asc", "");
        List<String> ids = getRunningCompetitions();
        return competitionManager.getFinder()
                .where()
                .eq("type", CompetitionType.ANONYMOUS.name())
                .in("id", ids)
                .findList();

    }

    /**
     * Checks whether the given competitionModel supports the user's language.
     * @param competitionModel
     * @return true if the competition does support the user's language, else false
     */
    private static boolean checkSupportedLanguages(CompetitionModel competitionModel){
        Language language;
        try {
            language = Language.getLanguage(EMessages.getLang());
        } catch (UnknownLanguageCodeException | UnavailableLanguageException ex){
            return false;
        }
        Competition competition = new Competition(competitionModel);
        return competition.getAvailableLanguages().contains(language);
    }
}
