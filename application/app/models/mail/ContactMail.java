/**
 *
 */
package models.mail;

import play.Play;
import models.EMessages;

/**
 * @author Jens N. Rammant
 * Class for mails sent by the Contact Form
 */
public class ContactMail extends EMail {

    /**
     * @param message Message of the mail
     * @param senderMail Email of the sender
     */
    public ContactMail(String message, String senderMail){
        super();
        this.setMessage(message);
        this.appendMessage("\n--------------\n");
        this.appendMessage(senderMail);
        this.setSubject(EMessages.get("contact.mail.subject"));
        this.addToAddress(Play.application().configuration().getString("email.contactmail"));
        this.addReplyTo(senderMail);
    }
}
