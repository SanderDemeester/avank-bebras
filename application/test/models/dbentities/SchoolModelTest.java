/**
 *
 */
package models.dbentities;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Test;

import test.ContextTest;

/**
 * @author Jens N. Rammant
 *
 */
public class SchoolModelTest extends ContextTest{

    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
        SchoolModel sm = new SchoolModel();
        sm.address="Teststraat 1";
        sm.id=1;
        sm.name="Test";

        try{
            sm.save();
        }catch(PersistenceException pe){
            Assert.fail(pe.toString());
        }
    }

}
