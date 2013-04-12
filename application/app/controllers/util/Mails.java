package controllers.util;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;
import play.mvc.Result;
import views.html.index;

import static play.mvc.Results.ok;

/**
 * Created with IntelliJ IDEA.
 * User: Eddy
 * Date: 10/04/13
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class Mails {

    public void sendMail(String recipient){
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("Password reset");
        mail.addRecipient(recipient);
        mail.addFrom("My Play App<noreply@email.com>");
        mail.send("Text","<html><body><p>You're password is reset.</p></body></html>");
    }
}
