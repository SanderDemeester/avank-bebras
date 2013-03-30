/**
 * 
 */
package models.dbentities;

import static org.junit.Assert.*;

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
		cg.id="a";
		
		try{
			cg.save();
		}catch(PersistenceException p){
			Assert.fail(p.toString());
		}
	}
	
	@Test(expected=PersistenceException.class)
	public void testDuplicateSave() {
		ClassGroup cg = new ClassGroup();
		cg.id="a";
		
		ClassGroup cg2 = new ClassGroup();
		cg2.id="a";
		
		cg.save();
		cg2.save();
	}

}
