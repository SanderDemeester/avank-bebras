/**
 *
 */
package models.user;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.PersistenceException;

import junit.framework.Assert;

import models.dbentities.ClassGroup;
import models.dbentities.ClassPupil;
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

}
