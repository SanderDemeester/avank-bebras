package controllers.User;

import play.mvc.Result;

public class Teacher extends SuperUser{
	
	public Teacher(){
		
	}

	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void scheduleUnrestrictedCompetition(){
		
	}
	
	public void searchStudents(){
		//TODO: Need to add some filtering system
	}
	
	public Result manageClasses(){
		return null;
	}
	
	public Result manageCompetitions(){
		return null;
	}

}
