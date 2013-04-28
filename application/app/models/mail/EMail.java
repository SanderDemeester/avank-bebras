/**
 * 
 */
package models.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import play.Play;

/**
 * @author Jens N. Rammant
 *
 */
public class EMail {
	
	private static Session session;
	private static String smtpUrl;
	private static int smtpPort;
	private static String smtpUser;
	private static String smtpPass;
	
	static{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(props);
        smtpUrl = Play.application().configuration().getString("smtp.url");
        smtpPort = Integer.parseInt(Play.application().configuration().getString("smtp.port"));
        smtpUser = Play.application().configuration().getString("smtp.user");
        smtpPass = Play.application().configuration().getString("smtp.pass");
	}
	
	private String message="";
	private String subject="";
	private List<InternetAddress> to;
	private List<InternetAddress> cc;
	private List<InternetAddress> replyTo;
	private Map<File,String> attachments;
	
	public EMail(){
		this.to = new ArrayList<InternetAddress>();
		this.cc = new ArrayList<InternetAddress>();
		this.replyTo = new ArrayList<InternetAddress>();
		this.attachments = new HashMap<File,String>();
	}
	
	public void setMessage(String message){
		this.message=message;
	}
	
	public void appendMessage(String extraMessage){
		if(this.message==null)this.message="";
		this.message = this.message + extraMessage;
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
	
	public boolean addReplyTo(String email){
		try {
			InternetAddress repl = new InternetAddress(email);
			this.replyTo.add(repl);
			return true;
		} catch (AddressException e) {
			return false;
		}
	}
	
	public void addAttachment(File attachment,String filename){
		this.attachments.put(attachment,filename);
	}
	
	public void send() throws MessagingException{
		Message message = new MimeMessage(session);
		if(!this.to.isEmpty()){
			message.setRecipients(RecipientType.TO,this.to.toArray(new Address[1]));	
		}
		if(!this.cc.isEmpty()){
			message.setRecipients(RecipientType.CC, this.cc.toArray(new Address[1]));
		}
		if(!this.replyTo.isEmpty()){
			message.setReplyTo(replyTo.toArray(new Address[1]));
		}		
		message.setSubject(subject);
		Multipart mp = new MimeMultipart();
		if(this.message!=null){
			MimeBodyPart text = new MimeBodyPart();
			text.setText(this.message);
			mp.addBodyPart(text);
		}
		for(File f : this.attachments.keySet()){
			MimeBodyPart attach = new MimeBodyPart();
			attach.setDataHandler(new DataHandler(new FileDataSource(f)));
			attach.setFileName(this.attachments.get(f));
			mp.addBodyPart(attach);
		}
		message.setContent(mp);
		Transport transport = session.getTransport("smtp");
        transport.connect(smtpUrl, smtpPort, smtpUser, smtpPass);
        transport.sendMessage(message, message.getAllRecipients());
        for(File f : this.attachments.keySet()){
        	f.delete();
        }
	}
	
	

}
