/**
 *
 */
package models.dbentities;

import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Test;

import test.ContextTest;

/**
 * @author Jens N. Rammant
 *
 */
public class FAQModelTest extends ContextTest {

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
    }

}
