package controllers.User;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Result;

public class Independent extends User{
	
	private List<String> previousClassList;
	
	public Independent(){
		super(); //abstract class constructor could init some values
		previousClassList = new ArrayList<String>();
	}
	
	//need to add documentation.
	public Independent(Independent independent){
		
	}

	@Override
	public Result showLandingPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addPreviousClass(String oldClass){
		previousClassList.add(oldClass);
	}
	
	public void addCurrentClass(ClassGroup classGroup){
		
	}
	
	public String getCurrentClass(){
		return null;
	}
	

}
