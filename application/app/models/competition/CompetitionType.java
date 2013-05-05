package models.competition;

import com.avaje.ebean.annotation.EnumValue;
import models.EMessages;

/**
 * An enumeration of contests types.
 *
 * @author Kevin Stobbelaar
 */
public enum CompetitionType {

    @EnumValue("UNRESTRICTED")
    UNRESTRICTED,

    @EnumValue("RESTRICTED")
    RESTRICTED,

    @EnumValue("ANONYMOUS")
    ANONYMOUS;

    @Override
    public String toString(){
        return EMessages.get("competition.type." + this.name().toLowerCase());
    }

}
