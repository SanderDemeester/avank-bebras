package test_suite_databank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.user.Independent;
import models.user.Organizer;
import models.user.Pupil;
import models.user.Teacher;
import models.user.User;
import models.user.UserID;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.specs2.internal.scalaz.Category.U;

import com.avaje.ebean.Ebean;

import java.security.SecureRandom;
import java.math.BigInteger;

import javax.persistence.PersistenceException;

import controllers.user.Type;
import static play.test.Helpers.*;

public class UserDatabaseTest{
	
	private SecureRandom random = new SecureRandom();
	
	@BeforeClass
	public static void startApp(){
	    Map<String, String> settings = new HashMap<String, String>();
		settings.put("db.default.driver", "org.h2.Driver");
	    settings.put("db.default.user", "sa");
	    settings.put("db.default.password", "");
	    settings.put("db.default.url", "jdbc:h2:mem:play");
	    
		start(fakeApplication(settings));
	}
	
	@AfterClass
	public static void stopApp(){
		stop(fakeApplication());
	}

	/*
	 * Test IndependentUser database insertion.
	 */
	@Test(expected = PersistenceException.class)
	public void makeIndependentUser() {
		String id = new BigInteger(130,random).toString();
		String name = "Bertrand Russell";
		User userFind = null;
		User user = new Independent(new UserID(id), Type.INDEPENDENT,name);
		
		try{
		user.save();
		}catch(PersistenceException e){}
		Assert.assertNotNull(Ebean.find(Independent.class).where().eq("id",user.id).findUnique());
		user.delete();
	}
	
	/*
	 * Test teacher database insertion.
	 */
	@Test(expected = PersistenceException.class)
	public void makeTeacher(){
		
		int numberOfTeachers = 10;
		String teacherID = new BigInteger(130,random).toString();
		String name = "Maya Angelou";
		
		for(int i = 0; i < numberOfTeachers; i++){
			try{
			new Teacher(new UserID(new BigInteger(130,random).toString()), Type.TEACHER, new BigInteger(130,random).toString()).save();
			}catch(PersistenceException e){}
		}
		
		List<User> teacherList = User.find.where().like("LoginType", "TEACHER").findList();
		Assert.assertTrue(teacherList.size() == numberOfTeachers);

		User teacher = new Teacher(new UserID(teacherID), Type.TEACHER, name);
		teacher.save();
		
		Assert.assertNotNull(Ebean.find(Teacher.class).where().eq("id",teacherID).findUnique());
		Assert.assertNotNull(Ebean.find(Teacher.class).where().eq("name", name).findUnique());
		
		teacher.delete();
	}
	
	@Test(expected = PersistenceException.class)
	public void makeOrganizer(){
		
		int numberOfOrganizer = 10;
		String organizerID = new BigInteger(130,random).toString();
		String name = "Maya Angelou";
		
		for(int i = 0; i < numberOfOrganizer; i++){
			try{
			new Organizer(new UserID(new BigInteger(130,random).toString()), Type.ORGANIZER, new BigInteger(130,random).toString()).save();
			}catch(PersistenceException e){}
		}
		
		List<User> organizerList = User.find.where().like("LoginType", "ORGANIZER").findList();
		Assert.assertTrue(organizerList.size() == numberOfOrganizer);

		User teacher = new Teacher(new UserID(organizerID), Type.ORGANIZER, name);
		teacher.save();
		
		Assert.assertNotNull(Ebean.find(Organizer.class).where().eq("id",organizerID).findUnique());
		Assert.assertNotNull(Ebean.find(Organizer.class).where().eq("name", name).findUnique());
		
		teacher.delete();
		
	}

	/**
	 * Test user finder.
	 */
	@Test(expected = PersistenceException.class)
	public void findListBasedOnType(){
		
		int numberOfIndependentUser = 10;
		int numberOfPupils = 5;
		
		//generate random IndependentUser and save them.
		for(int i = 0; i < numberOfIndependentUser; i++){
			try{
			new Independent(new UserID(new BigInteger(130,random).toString()),Type.INDEPENDENT,new BigInteger(130,random).toString()).save();
			}catch(PersistenceException e){}
		}
		
		//generate random Pupils.
		for(int i = 0; i < numberOfPupils; i++){
			try{
			new Pupil(new UserID(new BigInteger(130,random).toString()),Type.PUPIL,new BigInteger(130,random).toString()).save();
			}catch(PersistenceException e){}
		}
		
		List<User> allUsers = User.find.all();
		List<User> allIndepententUser = User.find.where().like("loginType", "INDEPENDENT").findList();
		List<User> allPupils = User.find.where().like("loginType","PUPIL").findList();
			
		Assert.assertTrue(allUsers.size() == numberOfIndependentUser+numberOfPupils);
		Assert.assertTrue(allIndepententUser.size() == numberOfIndependentUser);
		Assert.assertTrue(allPupils.size() == numberOfPupils);
	}
}
