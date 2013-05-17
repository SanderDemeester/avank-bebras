package models.mail;

import models.EMessages;
import play.Play;

public class StudentTeacherEmailReset extends EMail{
	
	/**
     * This method sends an email when the user requests a new password.
     * @param recipient email of destination
     * @param id the usermodel ID
     * @param url the url for resetting password
     */

    public StudentTeacherEmailReset(String recipient, String id, String url){
        super();
        this.setSubject(EMessages.get("forgot_pwd.teachersubject"));
        this.addReplyTo(Play.application().configuration().getString("email.contactmail"));
        this.addToAddress(recipient);
        String url1 = url.replace("<>",id);
        this.setMessage(EMessages.get("forgot_pwd.teachermail", id, url1));
    }
}
