package controllers.User;

import play.mvc.Result;

/**
 	The default abstract class for User
	@author sander
**/

public abstract class User {
	
	private UserID id;
	private String loginType;
	
	public String getUserInfo() {
		return null;
	}
	
	public void resetPassword(){
		
	}
	
	public void logout(){
		
	}
	
	public abstract Result showLandingPage();
	
	public Result showStatistics(){
		//TODO: add proper driver code.
		return null;
	}
	
	public Result showPersonalInformation(){
		//TODO: add proper driver code.
		return null;
	}
}
