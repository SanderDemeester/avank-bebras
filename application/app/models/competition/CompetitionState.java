package models.competition;

import models.EMessages;

/**
 * An enumeration of contests states
 *
 * @author Kevin Stobbelaar
 */
public enum CompetitionState {

    DRAFT,
    ACTIVE,
    RUNNING,
    FINISHED;


    @Override
    public String toString() {
        return EMessages.get("competition.state." + this.name().toLowerCase());
    }

}
