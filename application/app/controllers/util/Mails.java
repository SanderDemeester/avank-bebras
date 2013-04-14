package controllers.util;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;
import models.EMessages;
import play.mvc.Result;
import views.html.index;

import static play.mvc.Results.ok;

public class Mails {

    public void sendMail(String recipient){
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("Password reset");
        mail.addRecipient(recipient);
        mail.addFrom("Avank Bebras <avank@bebras.be>");
        mail.send(EMessages.get("forgot_pwd.success"));
    }
}
