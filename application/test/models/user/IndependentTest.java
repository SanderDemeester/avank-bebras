package models.user;

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

public class IndependentTest extends ContextTest {

	@Before
	public void clear(){
		List<UserModel> um = Ebean.find(UserModel.class).findList();
		for(UserModel u : um) u.delete();
		
		List<ClassGroup> cg = Ebean.find(ClassGroup.class).findList();
		for(ClassGroup c : cg) c.delete();
		
		List<ClassPupil> cp = Ebean.find(ClassPupil.class).findList();
		for(ClassPupil c:cp)c.delete();
	}
	
	@Test
	public void testGetCurrentClass() {
		UserModel data = createTestUserModel(UserType.INDEPENDENT);
		ClassGroup cl1 = new ClassGroup();
		cl1.id = "curr";
		ClassGroup cl2 = new ClassGroup();
		cl2.id = "p1";
		ClassGroup cl3 = new ClassGroup();
		cl3.id = "p2";
		data.classgroup = cl1.id;
		
		ClassPupil cp1 = new ClassPupil();
		cp1.classid=cl2.id;
		cp1.indid=data.id;
		ClassPupil cp2 = new ClassPupil();
		cp2.classid=cl3.id;
		cp2.indid=data.id;
		
		try{
			data.save();
			cl1.save();
			cl2.save();
			cl3.save();
			cp1.save();
			cp2.save();
		}catch(PersistenceException p){
			Assert.fail("Something went wrong with the saving of the independent");
		}
		
		UserModel find = Ebean.find(UserModel.class).where().eq("id", data.id).findUnique();
		Independent iff = new Independent(find);
		
		ClassGroup fff = iff.getCurrentClass();
		Assert.assertNotNull(fff);
		Assert.assertEquals(cl1.id, fff.id);
	}

	@Test
	public void testGetPreviousClasses() {
		UserModel data = createTestUserModel(UserType.INDEPENDENT);
		ClassGroup cl1 = new ClassGroup();
		cl1.id = "curr";
		ClassGroup cl2 = new ClassGroup();
		cl2.id = "p1";
		ClassGroup cl3 = new ClassGroup();
		cl3.id = "p2";
		data.classgroup = cl1.id;
		
		ClassPupil cp1 = new ClassPupil();
		cp1.classid=cl2.id;
		cp1.indid=data.id;
		ClassPupil cp2 = new ClassPupil();
		cp2.classid=cl3.id;
		cp2.indid=data.id;
		
		try{
			data.save();
			cl1.save();
			cl2.save();
			cl3.save();
			cp1.save();
			cp2.save();
		}catch(PersistenceException p){
			Assert.fail("Something went wrong with the saving of the independent");
		}
		
		UserModel find = Ebean.find(UserModel.class).where().eq("id", data.id).findUnique();
		Independent iff = new Independent(find);
		
		Collection<ClassGroup> fff = iff.getPreviousClasses();
		Assert.assertEquals(2, fff.size());
		HashSet<String> ids = new HashSet<String>();
		for(ClassGroup cg : fff){
			ids.add(cg.id);
		}
		Assert.assertTrue(ids.contains(cl2.id));
		Assert.assertTrue(ids.contains(cl3.id));
	}

}
