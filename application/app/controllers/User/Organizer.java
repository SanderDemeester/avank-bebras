package controllers.User;

import java.util.Set;

import play.mvc.Result;

public class Organizer extends SuperUser{
	
	public Organizer(){
		
	}

	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void blockPupil(User user){
		
	}
	
	public void mimickPupil(Independent user){
		
	}
	
	public Set<Teacher> listTeachers(){
		return null;
	}
	
	public Result manageQuestions(){
		return null;
	}
	
	public Result manageCompetitions(){
		return null;
	}
	

}
