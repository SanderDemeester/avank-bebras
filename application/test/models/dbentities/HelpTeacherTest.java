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
public class HelpTeacherTest extends ContextTest{

    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
        HelpTeacher ht = new HelpTeacher();
        ht.classid = 1;
        ht.teacherid ="1";
        try{
            ht.save();
        }catch(PersistenceException pe){
            Assert.fail("Saving failed");
        }
    }

}
