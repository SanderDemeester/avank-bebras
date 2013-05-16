package models.dbentities;

import org.junit.Assert;

import java.util.List;

import javax.persistence.PersistenceException;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import test.ContextTest;

/**
 * @author Jens N. Rammant
 *
 */
public class FAQModelTest extends ContextTest {

    @Before
    public void clear(){
        List<FAQModel> cp = Ebean.find(FAQModel.class).findList();
        for(FAQModel c:cp)c.delete();
    }

    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
        FAQModel ts = new FAQModel();
        ts.content = "Test";
        ts.language = "en";
        ts.name = "Testcase";

        try{
            ts.save();
        }catch(PersistenceException pe){
            Assert.fail(pe.toString());
        }

        // Test that this model is actually present in the database
        Assert.assertNotNull(Ebean.find(FAQModel.class).where().eq("name","Testcase").findUnique());
    }

}
