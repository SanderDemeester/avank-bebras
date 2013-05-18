package models.competition;

import models.EMessages;

/**
 * An enumeration of contests states
 *
 * @author Kevin Stobbelaar
 */
public enum CompetitionState {

    /**
     * draft
     */
    DRAFT,
    /**
     * active
     */
    ACTIVE,
    /**
     * running
     */
    RUNNING,
    /**
     * finished
     */
    FINISHED;


    @Override
    public String toString() {
        return EMessages.get("competition.state." + this.name().toLowerCase());
    }

}
