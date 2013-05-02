/**
 *
 */
package models.user;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.PersistenceException;

import junit.framework.Assert;

import models.dbentities.ClassGroup;
import models.dbentities.SchoolModel;
import models.dbentities.UserModel;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;


import test.ContextTest;
import static models.user.UserTests.createTestUserModel;

/**
 * @author Jens N. Rammant
 *
 */
public class TeacherTest extends ContextTest {

    @Before
    public void clear(){
        List<UserModel> um = Ebean.find(UserModel.class).findList();
        for(UserModel u : um) u.delete();

        List<ClassGroup> cg = Ebean.find(ClassGroup.class).findList();
        for(ClassGroup c : cg) c.delete();
        
        List<SchoolModel> sm = Ebean.find(SchoolModel.class).findList();
        for(SchoolModel s : sm)s.delete();
    }

    /**
     * Test method for {@link models.user.Teacher#getClasses()}.
     */
    @Test
    public void testGetClasses() {
        UserModel data = createTestUserModel(UserType.TEACHER);

        ClassGroup cp1=new ClassGroup();
        cp1.id=1;
        cp1.teacherid=data.id;

        ClassGroup cp2=new ClassGroup();
        cp2.id=2;
        cp2.teacherid=data.id;

        ClassGroup cp3=new ClassGroup();
        cp3.id=3;
        cp3.teacherid=data.id+data.id;

        try{
            data.save();
            cp1.save();
            cp2.save();
            cp3.save();
        }catch(PersistenceException p){
            Assert.fail("Something went wrong with the saving");
        }

        UserModel find = Ebean.find(UserModel.class).where().eq("id", data.id).findUnique();
        Teacher iff = new Teacher(find);

        Collection<ClassGroup> fff = iff.getClasses();
        Assert.assertEquals(2, fff.size());
        HashSet<Integer> ids = new HashSet<Integer>();
        for(ClassGroup cg : fff){
            ids.add(cg.id);
        }
        Assert.assertTrue(ids.contains(cp1.id));
        Assert.assertTrue(ids.contains(cp2.id));
    }

    @Test
    public void testGetSchools(){
    	UserModel data = createTestUserModel(UserType.TEACHER);
    	SchoolModel sm = new SchoolModel();
    	sm.address="rr";
    	sm.id=1;
    	sm.name="tt";
    	
    	SchoolModel sm2 = new SchoolModel();
    	sm2.address="rr";
    	sm2.id=2;
    	sm2.name="tt";
    	sm2.orig=data.id;
    	
    	SchoolModel sm3 = new SchoolModel();
    	sm3.address="rr";
    	sm3.id=3;
    	sm3.name="tt";
    	
    	ClassGroup cp1=new ClassGroup();
        cp1.id=1;
        cp1.teacherid=data.id;
        cp1.schoolid = sm3.id;
        
        try{
        	data.save();
        	sm.save();
        	sm2.save();
        	sm3.save();
        	cp1.save();
        }catch(PersistenceException pe){
        	Assert.fail("Something went wrong with the saving");
        }
        
        Collection<SchoolModel> coll = new Teacher(data).getSchools();
        Assert.assertEquals("not 2",2, coll.size());
        HashSet<Integer> ids = new HashSet<Integer>();
        for(SchoolModel s : coll){
            ids.add(s.id);
        }
        Assert.assertTrue("not orig",ids.contains(sm2.id));
        Assert.assertTrue("not class",ids.contains(sm3.id));
        
    }
    
    @Test
    public void testIsPupilsTeacher(){
    	UserModel t = createTestUserModel(UserType.TEACHER);
    	Teacher teacher = new Teacher(t);
    	t.id="t";
    	
    	ClassGroup cg1 = new ClassGroup();
    	cg1.id=1;
    	cg1.teacherid=t.id;
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, c.get(Calendar.YEAR)+1);
    	cg1.expdate = c.getTime();
    	
    	UserModel pup1 = createTestUserModel(UserType.PUPIL_OR_INDEP);
    	pup1.id="pup1";
    	UserModel pup2 = createTestUserModel(UserType.PUPIL_OR_INDEP);
    	pup2.id="pup2";
    	pup1.classgroup=cg1.id;
    	
    	try{
    		t.save();
    		cg1.save();
    		pup1.save();
    		pup2.save();
    	}catch(PersistenceException pe){
    		Assert.fail("Something went wrong with the saving");
    	}
    	
    	Assert.assertTrue("f1",teacher.isPupilsTeacher(pup1.id));
    	Assert.assertFalse("f2",teacher.isPupilsTeacher(pup2.id));
    	
    }
}
