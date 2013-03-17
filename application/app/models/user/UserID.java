
package models.user;

/**
 * Modeling UserID
 * @author Sander Demeester
 */
public class UserID {

    private String userID;

    public UserID(String UserID){
        this.userID = userID;
    }

    /**
     *
     * @return Get the users ID.
     */
    public String geUserID(){
        return userID;
    }

    /**
     * Add userID to user.
     * @param UserID
     */
    public void setUserID(String UserID){
        this.userID = userID;
    }

}