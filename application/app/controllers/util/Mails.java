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

    public void sendMail(){
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("test");
        mail.addRecipient("eddy_vdh@live.be");
        mail.addFrom("My Play App<noreply@email.com>");
        mail.send("Text","<html><body><p>Dit is een test.</p></body></html>");
    }
}
