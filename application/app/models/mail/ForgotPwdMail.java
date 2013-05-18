package models.mail;


import models.EMessages;
import play.Play;

public class ForgotPwdMail extends EMail {

     /**
     * This method sends an email when the user requests a new password.
     * @param recipient email of destination
     * @param id the usermodel ID
     * @param url the url for resetting password
     */
    public ForgotPwdMail(String recipient, String id, String url) {
        super();
        this.setSubject(EMessages.get("forgot_pwd.mail_subject"));
        this.addReplyTo(Play.application().configuration().getString("email.contactmail"))         ;
        this.addToAddress(recipient);
        this.setMessage(EMessages.get("forgot_pwd.mail_sent", id, url));
    }
}
