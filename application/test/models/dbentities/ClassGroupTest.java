/**
 *
 */
package models.dbentities;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class ClassGroupTest extends ContextTest {

    @Before
    public void clear(){
        List<ClassGroup> cp = Ebean.find(ClassGroup.class).findList();
        for(ClassGroup c:cp)c.delete();
    }

    /**
     * Test method for {@link play.db.ebean.Model#save()}.
     */
    @Test
    public void testSave() {
        ClassGroup cg = new ClassGroup();
        cg.id=1;

        try{
            cg.save();
        }catch(PersistenceException p){
            Assert.fail(p.toString());
        }
        
        // Test that this model is actually present in the database
        assertNotNull(Ebean.find(ClassGroup.class).where().eq("id",1).findUnique());
    }

    @Test(expected=PersistenceException.class)
    public void testDuplicateSave() {
        ClassGroup cg = new ClassGroup();
        cg.id=1;

        ClassGroup cg2 = new ClassGroup();
        cg2.id=1;

        cg.save();
        cg2.save();
    }
    
    @Test
    public void testIsActive(){
    	Calendar c = new GregorianCalendar(2015, 3, 14);
    	Date piDay = c.getTime();
    	
    	ClassGroup cg = new ClassGroup();
    	cg.expdate = piDay;
    	
    	Assert.assertTrue("Today case failed",cg.isActive(piDay));
    	c.add(Calendar.HOUR, 5);
    	Date someHoursLater = c.getTime();
    	Assert.assertTrue("Hours later case failed",cg.isActive(someHoursLater));
    	
    	c.add(Calendar.DATE, -1);
    	Date dayBefore = c.getTime();
    	Assert.assertTrue("Day before case failed",cg.isActive(dayBefore));
    	
    	c.add(Calendar.DATE, 2);
    	Date dayAfter = c.getTime();
    	Assert.assertFalse("Day after case failed",cg.isActive(dayAfter));
    }

}
