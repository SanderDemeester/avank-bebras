package models.question.server;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.EMessages;
import models.data.Link;
import models.management.Editable;
import models.management.Listable;
import models.management.ManageableModel;
import models.question.Question;
import models.question.QuestionBuilderException;
import models.question.QuestionIO;
import models.question.QuestionPack;
import play.data.validation.Constraints;
import views.html.question.editor.create;

/**
 * ServerController entity managed by Ebean.
 *
 * @author Ruben Taelman, Kevin Stobbelaar
 *
 */
@Entity
@Table(name="servers")
public class Server extends ManageableModel implements Listable{
    private static final long serialVersionUID = 1L;

    // TODO database aanpassen zodat een server een unieke id krijgt, die niet de naam van de server is !

    @Editable(uponCreation=true)
    @Id
    @Column(name="id")
    public String id;

    @Editable
    @Column(name="location")
    @Constraints.Required
    public String path;

    @Editable
    @Constraints.Required
    public String ftpuri;

    @Editable
    @Constraints.Required
    public Integer ftpport;

    @Editable
    @Constraints.Required
    public String ftpuser;

    @Editable
    @Constraints.Required
    public String ftppass;
    
    @Editable
    @Constraints.Required
    public String ftppath;

    public static Finder<String, Server> finder = new Finder<String, Server>(String.class, Server.class);

    public static Server findById(String name) {
        return finder.byId(name);
    }

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {this.id, this.path};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }

    @Override
    public Map<String, String> options() {
        List<Server> servers = finder.all();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Server server: servers) {
            options.put(server.id, server.id);
        }
        return options;
    }
    
    public void testConnection() throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException {
        // Connect to server
        FTPClient client = new FTPClient();
        client.connect(ftpuri, ftpport);
        client.login(ftpuser, ftppass);
        
        // TODO: non-static dit
        client.changeDirectory(ftppath);
        
        // Close server connection
        client.disconnect(true);
    }
    
    public void sendFile(String questionID, File compressedQuestion, String userID) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException {
        FTPClient client = new FTPClient();
        
        // Connect to server
        client.connect(ftpuri, ftpport);
        client.login(ftpuser, ftppass);
        // TODO: non-static dit
        client.changeDirectory(ftppath);
        client.createDirectory(questionID);
        client.changeDirectory(questionID);
        
        // Extract question file
        ZipInputStream zis = new ZipInputStream(new FileInputStream(compressedQuestion));
        
        // Loop over the entries
        ZipEntry entry = zis.getNextEntry();
        while(entry != null) {
            File file = QuestionIO.addTempFile(
                            QuestionIO.getUserUploadLocation(userID),
                            entry.getName()
                        );
            QuestionIO.copyStream(zis, new FileOutputStream(file));
            
            // Upload the new file
            client.upload(file);
            
            // Close everything to avoid leaks
            zis.closeEntry();
            entry = zis.getNextEntry();
        }
        zis.close();
        
        // We are nice and close the connection
        client.disconnect(true);
    }

}
