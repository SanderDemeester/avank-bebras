package models.competition;


import models.question.Answer;
import models.user.User;

import java.sql.Time;
import java.util.List;

/**
 * Competition user state class.
 * TODO: DEPRECATED, TO BE REMOVED
 *
 * @author Kevin Stobbelaar.
 */

public class CompetitionUserState {

    private Time startTime;
    private Time endTime;
    private String cookie;
    private User user;
    private Competition competition;

    /**
     * Constructor
     * @param user corresponding user
     * @param competition corresponding competition
     */
    public CompetitionUserState(User user, Competition competition){
        this.user = user;
        this.competition = competition;
        this.cookie = null;
    }

    /**
     * Updates the current competition user state by setting a updated cookie.
     * @param cookie new cookie
     */
    public void update(String cookie){
        this.cookie = cookie;
    }

    /**
     * Converts the current cookie to a list of answers.
     * @return answer list
     */
    public List<Answer> convertCookieToAnswerList(){
        throw new UnsupportedOperationException();
    }

    /**
     * Gives the user an amount of grace time.
     * @param time grace time granted
     * @param cookie cookie
     */
    public void addGraceTime(int time, String cookie){
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the time for this state.
     * @param time time
     */
    public void setTime(int time){
        throw new UnsupportedOperationException();
    }


}
