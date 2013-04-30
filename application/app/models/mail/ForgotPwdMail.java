package models.mail;

import models.EMessages;
import play.Play;

public class ForgotPwdMail extends EMail {

    public ForgotPwdMail(String recipient, String id, String password) {
        super();
        this.setSubject(EMessages.get("forgot_pwd.mail_subject"));
        this.addReplyTo(recipient)         ;
        this.addToAddress(Play.application().configuration().getString("email.contactmail"));
        this.setMessage(EMessages.get("forgot_pwd.mail_sent", id, password));
    }
}
