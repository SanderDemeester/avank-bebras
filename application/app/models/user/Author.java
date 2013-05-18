package models.user;

import models.dbentities.UserModel;

/**
 * @author Sander Demeester
 */
public class Author extends Authenticated{
    /**
     * Constructor
     * @param Data model class
     */
    public Author(UserModel data) {
        super(data, UserType.AUTHOR);
        ROLES.add(Role.QUESTIONEDITOR);
    }

}
