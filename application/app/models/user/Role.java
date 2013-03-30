package models.user;

/**
 * An enumeration of all the possible roles a user can have
 * @author Ruben Taelman
 *
 */
public enum Role {

    // Anon
    LOGIN,
    REGISTER,
    
    // Authenticated
    LANDINGPAGE,
    CHANGEPASSWORD,
    
    // Organiser
    CREATEQUESTION,
    READQUESTION,
    UPDATEQUESTION,
    DELETEQUESTION,
    
    // Author
    QUESTIONEDITOR;

}
