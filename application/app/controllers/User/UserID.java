package controllers.User;


/**
	Moddeling UserID
	@author sander
**/

public class UserID {
	
	private String userID;
	
	public UserID(String UserID){
		this.userID = userID;
	}
	
	public String geUserID(){
		return userID;
	}
	
	public void setUserID(String UserID){
		this.userID = userID;
	}

}
