/**
 *
 */
package controllers.classgroups;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import models.dbentities.ClassGroup;
import models.dbentities.UserModel;
import models.user.Gender;
import models.user.UserType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import controllers.util.DateFormatter;

import test.ContextTest;

/**
 * @author Jens N. Rammant
 *
 */
public class ClassGroupIOTest extends ContextTest{

    @Before
    public void cleanDB(){
        List<UserModel> users = Ebean.find(UserModel.class).findList();
        for(UserModel um : users)um.delete();
        List<ClassGroup> cgs = Ebean.find(ClassGroup.class).findList();
        for(ClassGroup cg : cgs)cg.delete();
    }

    /**
     * Test method for {@link controllers.classgroups.ClassGroupIO#fullClassgroupToList(int)}.
     */
    @Test
    public void testFullClassgroupToList() {
        ClassGroup cg = new ClassGroup();
        cg.id=1;
        cg.name="test";
        cg.expdate=new Date(17);
        cg.level="t";
        cg.teacherid="aa";
        cg.schoolid=2;

        UserModel um = new UserModel();
        um.type=UserType.PUPIL_OR_INDEP;
        um.id="um";
        um.name="name";
        um.birthdate = new Date(15);
        um.gender=Gender.Male;
        um.preflanguage="en";
        um.password="pass";
        um.hash="hash";
        um.registrationdate=new Date(1);
        um.classgroup=cg.id;

        try{
            cg.save();
            um.save();
        }catch(PersistenceException pe){
            pe.printStackTrace();
            Assert.fail("Something went wrong with the saving");
        }

        List<List<String>> res = ClassGroupIO.fullClassgroupToList(cg.id);
        Assert.assertEquals(res.size(), 4);
        Assert.assertEquals(res.get(0).get(0), "#");
        Assert.assertEquals(res.get(2).get(0), "#");
        Assert.assertEquals(res.get(1).get(0), "CLASS");
        Assert.assertEquals(res.get(1).get(1), cg.name);
        Assert.assertEquals(res.get(1).get(2), DateFormatter.formatDate(cg.expdate));
        Assert.assertEquals(res.get(1).get(3), cg.level);
        Assert.assertEquals(res.get(1).get(4), Integer.toString(cg.schoolid));
        Assert.assertEquals(res.get(3).get(0), "PUPIL");
        Assert.assertEquals(res.get(3).get(1), um.id);
        Assert.assertEquals(res.get(3).get(2), um.name);
        Assert.assertEquals(res.get(3).get(3), DateFormatter.formatDate(um.birthdate));
        Assert.assertEquals(res.get(3).get(4), um.gender.toString());
        Assert.assertEquals(res.get(3).get(5), um.preflanguage);
        Assert.assertEquals(res.get(3).get(6), "");

    }


}
