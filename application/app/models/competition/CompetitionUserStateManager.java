package models.competition;

import java.util.*;

import com.avaje.ebean.Ebean;
import models.EMessages;
import models.data.DataDaemon;
import models.dbentities.CompetitionModel;
import models.question.QuestionSet;
import models.user.Anon;
import models.user.User;

/**
 * Manages the different CompetitionUserStates from each competition
 * @author Ruben Taelman
 *
 */
public class CompetitionUserStateManager {

    private static CompetitionUserStateManager _instance;
    // Maps the competition-id to a map of user-ids with their states for that competition
    private Map<String, Map<String, CompetitionUserState>> states;

    private CompetitionUserStateManager() {
        states = new HashMap<String, Map<String, CompetitionUserState>>();
    }

    /**
     * This will be called everytime the singleton instance of this class is requested.
     */
    public void whileGetInstance() {
        List<CompetitionModel> competitionModels = Ebean.find(CompetitionModel.class).where()
                .lt("starttime", new Date())
                .gt("endtime", new Date())
                .findList();

        for (CompetitionModel competitionModel : competitionModels){
            if (!isCompetitionStarted(competitionModel.id)){
                startCompetition(new Competition(competitionModel));
            }
        }
    }

    /**
     * Get the unique instance of this manager
     * @return unique instance
     */
    public static CompetitionUserStateManager getInstance() {
        if(_instance == null)
            _instance = new CompetitionUserStateManager();
        _instance.whileGetInstance();
        return _instance;
    }

    /**
     * Start a competition, will auto-expire
     * @param competition
     */
    public void startCompetition(final Competition competition) {
        states.put(competition.getID(), new HashMap<String, CompetitionUserState>());

        // Auto-expire setup
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(competition.getExpirationDate());
        DataDaemon.getInstance().runAt(new Runnable(){

            @Override
            public void run() {
                try {
                    finishCompetition(competition.getID());
                    competition.setState(CompetitionState.FINISHED);
                } catch (CompetitionNotStartedException e) {
                    // Silently fail, this means that the competition was ended before
                }
            }

        }, calendar);
        competition.setState(CompetitionState.RUNNING);
    }

    /**
     * Adds a new competitionuserstate for the given user to the given competition
     * @param competitionID the id of the competition
     * @param questionSet the id of the questionset taken by the user
     * @param user the user
     * @throws CompetitionNotStartedException if the competition has not been started yet, be sure to
     * call startCompetition(competition) first.
     */
    public void registerUser(String competitionID, QuestionSet questionSet, User user) throws CompetitionNotStartedException {
        Map<String, CompetitionUserState> list = getStates(competitionID);
        // Change lang to user.data.preflanguage if the current way should give any problems,
        // The current way will allow registering users who have chosen a different language
        // for executing the competition than their current preferred language.
        list.put(user.getID(), new CompetitionUserState(user, questionSet, EMessages.getLang()));
    }

    /**
     * Adds a new competitionuserstate for an anonymous user to the given competition
     * @param competitionID the id of the competition
     * @param questionSet the id of the questionset taken by the user
     * @param token a unique token for this user, could be the cookie
     * @throws CompetitionNotStartedException if the competition has not been started yet, be sure to
     * call startCompetition(competition) first.
     */
    public void registerAnon(String competitionID, QuestionSet questionSet, String token) throws CompetitionNotStartedException {
        Map<String, CompetitionUserState> list = getStates(competitionID);
        list.put(token, new CompetitionUserState(new Anon(), questionSet, EMessages.getLang()));
    }

    /**
     * Finish a competiton and save all the states associated with it
     * @param competitionID the competition id
     * @throws CompetitionNotStartedException if the competition has not been started yet, be sure to
     * call startCompetition(competition) first.
     */
    public void finishCompetition(String competitionID)
            throws CompetitionNotStartedException{
        Map<String, CompetitionUserState> list = getStates(competitionID);

        for(CompetitionUserState state : states.get(competitionID).values()) {
            state.save();
        }

        states.remove(competitionID);
    }

    /**
     * Get the state for a certain user in a competition
     * @param competitionID the id of the competition
     * @param userID the id of the user
     * @return the state for the given user in the given competition
     * @throws CompetitionNotStartedException if the competition has not been started yet, be sure to
     * call startCompetition(competition) first.
     */
    public CompetitionUserState getState(String competitionID, String userID) throws CompetitionNotStartedException {
        return getStates(competitionID).get(userID);
    }

    private Map<String, CompetitionUserState> getStates(String competitionID) throws CompetitionNotStartedException {
        Map<String, CompetitionUserState> list = states.get(competitionID);
        if(list == null) throw new CompetitionNotStartedException(
                "This competition has not yet been started.");
        return list;
    }

    private boolean isCompetitionStarted(String competitionID) {
        try {
            getStates(competitionID);
        } catch (CompetitionNotStartedException e) {
            return false;
        }
        return true;
    }

    /**
     * Counts the amount of registered users for a certain competition that are started
     * @param competitionID the id of the competition
     * @return the amount of pupils in a competition
     * @throws CompetitionNotStartedException if the competition has not been started yet, be sure to
     * call startCompetition(competition) first.
     */
    public int getAmountRegistered(String competitionID) throws CompetitionNotStartedException{
        Map<String, CompetitionUserState> list = states.get(competitionID);
        if(list == null) throw new CompetitionNotStartedException(
                "This competition has not yet been started.");
        return list.size();
    }

    /**
     * Counts the amount of registered users for a certain competition that are started
     * @param competitionID the id of the competition
     * @return the amount of pupils in a competition
     * @throws CompetitionNotStartedException if the competition has not been started yet, be sure to
     * call startCompetition(competition) first.
     */
    public int getAmountFinished(String competitionID) throws CompetitionNotStartedException{
        int finished = 0;
        Map<String, CompetitionUserState> list = states.get(competitionID);
        if(list == null) throw new CompetitionNotStartedException(
                "This competition has not yet been started.");
        for(CompetitionUserState state : states.get(competitionID).values()) {
            if(state.isFinished()) finished++;
        }
        return finished;
    }
}
