/**
 *
 */
package models.user;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.PersistenceException;

import models.dbentities.UserModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import test.ContextTest;

/**
 * @author Jens N. Rammant
 *
 */
public class ChainOfCommandTest extends ContextTest{

    @Before
    public void cleanDB(){
        List<UserModel> l = Ebean.find(UserModel.class).findList();
        for(UserModel um : l)um.delete();
    }

    /**
     * Test method for {@link models.user.ChainOfCommand#isSuperiorOf(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testIsSuperiorOfStringString() {
        UserModel um1 = UserTests.createTestUserModel(UserType.ADMINISTRATOR);
        um1.id="1";
        UserModel um2 = UserTests.createTestUserModel(UserType.ORGANIZER);
        um2.id="2";

        try{
            um1.save();
            um2.save();
        }catch(PersistenceException pe){
            Assert.fail("Something went wrong with the saving");
        }

        Assert.assertTrue(ChainOfCommand.isSuperiorOf(um1.id, um2.id));
        Assert.assertFalse(ChainOfCommand.isSuperiorOf(um2.id, um1.id));
    }

    /**
     * Test method for {@link models.user.ChainOfCommand#isSuperiorOf(models.user.User, models.user.User)}.
     */
    @Test
    public void testIsSuperiorOfUserUser() {
        UserModel um1 = UserTests.createTestUserModel(UserType.ADMINISTRATOR);
        um1.id="1";
        UserModel um2 = UserTests.createTestUserModel(UserType.ORGANIZER);
        um2.id="2";

        try{
            um1.save();
            um2.save();
        }catch(PersistenceException pe){
            Assert.fail("Something went wrong with the saving");
        }

        Assert.assertTrue(ChainOfCommand.isSuperiorOf(new Independent(um1), new Independent(um2)));
        Assert.assertFalse(ChainOfCommand.isSuperiorOf(new Independent(um2),new Independent(um1)));
    }

}
