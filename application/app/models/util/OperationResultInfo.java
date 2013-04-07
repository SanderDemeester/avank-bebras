/**
 * 
 */
package models.util;

import java.util.ArrayList;

/**
 * @author Jens N. Rammant
 *A class to be used to easily pass info (warning, success, error, info) about the result
 * of an operation to a view. Categories were chosen in accordance with the options
 * Bootstrap provides.
 */
public class OperationResultInfo {
	private ArrayList<String> success;
	private ArrayList<String> info;
	private ArrayList<String> warning;
	private ArrayList<String> error;
	
	public OperationResultInfo(){
		success = new ArrayList<String>();
		info = new ArrayList<String>();
		warning  = new ArrayList<String>();
		error  = new ArrayList<String>();
	}
	
	public void add(String message,Type type){
		switch (type) {
		case ERROR:
			error.add(message);
			break;
		case INFO:
			info.add(message);
			break;
		case SUCCESS:
			success.add(message);
			break;
		case WARNING:
			warning.add(message);
			break;
		default:
			break;
		
		}
	}
	
	public ArrayList<String> getList(Type type){
		switch (type){
		case ERROR:
			return error;
		case INFO:
			return info;
		case SUCCESS:
			return success;
		case WARNING:
			return warning;
		default:
			return new ArrayList<String>();
			
		}
	}
	
	public enum Type{
		SUCCESS,INFO,WARNING,ERROR
	}
	
}
