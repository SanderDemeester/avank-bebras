/**
 *
 */
package models.dbentities;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;

import models.question.Server;
import models.user.Gender;
import models.user.UserType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.ContextTest;

import com.avaje.ebean.Ebean;

/**
 * @author Ruben Taelman
 *
 */
public class QuestionModelTest extends ContextTest {

    private UserModel author;
    private Server server;

    @Before
    public void clearAndMake(){
        List<QuestionModel> cp = Ebean.find(QuestionModel.class).findList();
        for(QuestionModel c:cp) c.delete();

        List<UserModel> um = Ebean.find(UserModel.class).findList();
        for(UserModel u:um) u.delete();

        List<Server> sm = Ebean.find(Server.class).findList();
        for(Server s:sm) s.delete();

        this.author = makeUserModel();
        this.server = makeServer();
    }

    private UserModel makeUserModel() {
        // Make a dummy author
        UserModel author = new UserModel("imanauthor", UserType.AUTHOR, "Bob De Auteur",
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                "unimportant password", "unimportant hash", "email@unimportant.com",
                Gender.Male, "en");
        try{
            author.save();
        } catch(PersistenceException pe) {
            Assert.fail(pe.toString());
        }
        return author;
    }

    private Server makeServer() {
        // Make a dummy server
        Server server = new Server();
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
        return server;
    }

    private QuestionModel makeQuestionModel(UserModel author, Server server) {
        // Make a question model
        QuestionModel model = new QuestionModel(author, "some path");
        model.active = true;
        model.officialid = "unique";
        model.server = server;

        return model;
    }

    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
        QuestionModel model = makeQuestionModel(author, server);
        try{
            model.save();
        } catch(PersistenceException pe) {
            Assert.fail(pe.toString());
        }

        // Test that this model is actually present in the database
        assertNotNull(Ebean.find(QuestionModel.class).where().eq("officialid","unique").findUnique());
    }

    /**
     * Test a duplicate save
     */
    @Test(expected=PersistenceException.class)
    public void testDuplicateSave() {
        QuestionModel model1 = makeQuestionModel(author, server);
        model1.id=10;
        QuestionModel model2 = makeQuestionModel(author, server);
        model2.id=10;

        model1.save();
        model2.save();
    }

    /**
     * Test a duplicate save with our own check
     */
    @Test
    public void testDuplicateSaveCheck() {
        QuestionModel model1 = makeQuestionModel(author, server);
        model1.id=10;
        QuestionModel model2 = makeQuestionModel(author, server);
        model2.id=10;

        model1.save();
        Assert.assertFalse(model2.isUnique());
    }

    /**
     * Test if the fixServer() method works as expected
     */
    @Test
    public void testFixServer() {
        QuestionModel model = makeQuestionModel(author, server);
        model.server = new Server();
        model.server.id = server.id;
        model.fixServer();

        Assert.assertEquals(model.server, server);
    }

}
