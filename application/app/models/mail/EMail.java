/**
 * 
 */
package models.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Jens N. Rammant
 *
 */
public class EMail {
	
	private String message="";
	private String subject="";
	private List<InternetAddress> to;
	private List<InternetAddress> cc;
	//TODO lookup replyTo & attachment & sender
	
	public EMail(){
		this.to = new ArrayList<InternetAddress>();
		this.cc = new ArrayList<InternetAddress>();
	}
	
	public void setMessage(String message){
		this.message=message;
	}
	
	public void appendMessage(String extraMessage){
		this.message = this.message + extraMessage; //TODO check if message null
	}
	
	public void setSubject(String subject){
		this.subject=subject;
	}
	
	public boolean addToAddress(String email){
		try {
			InternetAddress add = new InternetAddress(email);
			this.to.add(add);
			return true;
		} catch (AddressException e) {
			return false;
		} 
		
	}
	
	public boolean addCCAddress(String email){
		try {
			InternetAddress cc = new InternetAddress(email);
			this.cc.add(cc);
			return true;
		} catch (AddressException e) {
			return false;
		} 
		
	}
	
	public void send(){
		//TODO everything
	}
	
	

}
