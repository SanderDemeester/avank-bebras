package models.user;

import java.util.LinkedHashMap;
import java.util.Map;

import models.EMessages;
import models.management.Listable;

/**
 * Enumerates the different types of user. The earlier in the list, the more rights the user has
 * @author Sander Demeester, Ruben Taelman
 */
public enum UserType {

    ADMINISTRATOR,
    ORGANIZER,
    TEACHER,
    AUTHOR,
    PUPIL_OR_INDEP,
    ANON;
    
}
