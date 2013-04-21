package models.question;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.Editable;
import models.management.Listable;
import models.management.ManageableModel;
import play.Logger;
import play.data.validation.Constraints;

/**
 * Server entity managed by Ebean.
 *
 * @author Ruben Taelman
 * @author Kevin Stobbelaar
 *
 */
@Entity
@Table(name="servers")
public class Server extends ManageableModel implements Listable{
    private static final long serialVersionUID = 2L;
    
    @Editable(uponCreation=true)
    @Id
    @Column(name="id")
    public String id;

    @Editable
    @Column(name="location")
    @Constraints.Required
    public String path;

    @Editable(hiddenInList=true)
    @Constraints.Required
    public String ftpuri;

    @Editable(hiddenInList=true)
    @Constraints.Required
    public Integer ftpport;

    @Editable(hiddenInList=true)
    @Constraints.Required
    public String ftpuser;

    @Editable(hiddenInList=true)
    @Constraints.Required
    public String ftppass;
    
    @Editable(hiddenInList=true)
    @Constraints.Required
    public String ftppath;
    
    @Editable(hiddenInList=true)
    @Constraints.Required
    public boolean is_http_secured;
    
    @Editable(hiddenInList=true)
    @Constraints.Required
    public String http_username;
    
    @Editable(hiddenInList=true)
    @Constraints.Required
    public String http_password;

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
    
    /**
     * Set the correct authentication if needed. To be able to perform requests to the server.
     */
    public void setAuthentication() {
        if(is_http_secured) Authenticator.setDefault(new ServerAuthenticator());
        else Authenticator.setDefault(null);
    }
    
    /**
     * A method to test the connection with the server. FTP and HTTP
     * @throws IllegalStateException wrong ftp state
     * @throws IOException error while testing IO
     * @throws FTPIllegalReplyException error with the ftp server
     * @throws FTPException another ftp error
     */
    public void testConnection() throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException {
        // Connect to server
        FTPClient client = new FTPClient();
        client.connect(ftpuri, ftpport);
        client.login(ftpuser, ftppass);
        
        // TODO: non-static dit
        client.changeDirectory(ftppath);
        
        // Close server connection
        client.disconnect(true);
        
        // Test the http connection
        setAuthentication();
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        int code = connection.getResponseCode();
        if(code == 401) throw new IOException("Can't connect to the server with the provided HTTP credentials.");
    }
    
    /**
     * Send a certain file to this server
     * @param questionID the ID of the question
     * @param compressedQuestion zip file of the question
     * @param userID id of the user
     * @throws IllegalStateException wrong ftp state
     * @throws IOException error with io
     * @throws FTPIllegalReplyException error with ftp server
     * @throws FTPException another ftp error
     * @throws FTPDataTransferException error while sending file
     * @throws FTPAbortedException transfer was aborted
     */
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
    
    private class ServerAuthenticator extends Authenticator {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(http_username, http_password.toCharArray());
        }
    }

}
