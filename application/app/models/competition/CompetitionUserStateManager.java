package models.competition;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import models.EMessages;
import models.data.DataDaemon;
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
     * Get the unique instance of this manager
     * @return
     */
    public static CompetitionUserStateManager getInstance() {
        if(_instance == null)
            _instance = new CompetitionUserStateManager();
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
        list.put(user.getID(), new CompetitionUserState(user, questionSet, user.data.preflanguage));
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
}
