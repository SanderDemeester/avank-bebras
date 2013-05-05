package models.mail;

import models.EMessages;
import play.Play;

public class ForgotPwdMail extends EMail {

    public ForgotPwdMail(String recipient, String id, String url) {
        super();
        this.setSubject(EMessages.get("forgot_pwd.mail_subject"));
        this.addReplyTo(Play.application().configuration().getString("email.contactmail"))         ;
        this.addToAddress(recipient);
        this.setMessage(EMessages.get("forgot_pwd.mail_sent", id, url));
    }
}
