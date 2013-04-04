package models.user;

/**
 * Enumerates the different types of user. The earlier in the list, the more rights the user has
 * @author Sander Demeester, Ruben Taelman
 */
public enum UserType {

    ADMINISTRATOR,
    ORGANIZER,
    TEACHER,
    AUTHOR,
    PUPIL,
    INDEPENDENT,
    ANON;

}
