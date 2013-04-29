package models.question;

import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;
import java.util.List;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import test.ContextTest;

import static org.junit.Assert.assertNotNull;

/**
 *
 * Test for the paging mechanism of the servers and Ebean saving
 *
 * @author Kevin Stobbelaar
 * @author Ruben Taelman
 *
 */
public class ServerTest extends ContextTest{
    
    @Before
    public void clear(){
        List<Server> sm = Ebean.find(Server.class).findList();
        for(Server s:sm) s.delete();
    }

    @Test
    public void testPage() {
    }
    
    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
     // Make a dummy server
        Server server = new Server();
        server.id = "servername";
        server.path = "";
        server.ftppass = "";
        server.ftppath = "";
        server.ftpport = 0;
        server.ftpuri = "";
        server.ftpuser = "";
        try{
            server.save();
        } catch(PersistenceException pe) {
            Assert.fail(pe.toString());
        }
        
        // Test that this model is actually present in the database
        assertNotNull(Ebean.find(Server.class).where().eq("id","servername").findUnique());
    }
    
    /**
     * Test the server connection with an ftp server we know is valid
     */
    @Ignore @Test
    public void testconnection() {
        // Make an empty dummy server
        Server server = new Server();
        server.ftppass = "avank";
        server.ftppath = "avank.rubensworks.net/questions";
        server.ftpuri = "ftp.freehostia.com";
        server.ftpport = 21;
        server.ftpuser = "rubtae";
        
        try {
            server.testConnection();
        } catch (IllegalStateException | IOException | FTPIllegalReplyException
                | FTPException e) {
            Assert.fail(e.toString());
        }
    }
    
    @Test(expected=PersistenceException.class)
    public void testDuplicateSave() {
        Server server1 = new Server();
        server1.id = "name";
        Server server2 = new Server();
        server2.id = "name";

        server1.save();
        server2.save();
    }
}
