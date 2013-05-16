/**
 *
 */
package models.dbentities;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.PersistenceException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import test.ContextTest;

/**
 * @author Jens N. Rammant
 *
 */
public class ClassPupilTest extends ContextTest {

    @Before
    public void clear(){
        List<ClassPupil> cp = Ebean.find(ClassPupil.class).findList();
        for(ClassPupil c:cp)c.delete();
    }

    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
        ClassPupil cp = new ClassPupil();
        cp.indid="a";
        cp.classid=1;

        try{
            cp.save();
        }catch(PersistenceException p){
            Assert.fail(p.toString());
        }

        // Test that this model is actually present in the database
        assertNotNull(Ebean.find(ClassPupil.class).where().eq("indid","a").findUnique());
    }

    @Test(expected=PersistenceException.class)
    public void testDuplicateSave() {
        ClassPupil cp = new ClassPupil();
        cp.indid="a";
        cp.classid=1;

        ClassPupil cp2 = new ClassPupil();
        cp2.indid="a";
        cp2.classid=1;

        cp.save();
        cp2.save();
    }

}
