///**
// * 
// */
//package models.user;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
///**
// * @author Jens N. Rammant
// *
// */
//public class LoginStateTest {
//	
//	private LoginState toTest;
//
//	/**
//	 * Test method for {@link models.user.LoginState#getID()}.
//	 */
//	@Test
//	public void testGetID() {
//		toTest = new LoginState("session");
//		Assert.assertNull(toTest.getID());
//		toTest.login("test");
//		Assert.assertEquals("test", toTest.getID());
//		toTest.mimic("mimic");
//		Assert.assertEquals("mimic",toTest.getID());
//	}
//
//	/**
//	 * Test method for {@link models.user.LoginState#isLoggedIn()}.
//	 */
//	@Test
//	public void testIsLoggedIn() {
//		toTest = new LoginState("session");
//		Assert.assertFalse(toTest.isLoggedIn());
//		toTest.login("test");
//		Assert.assertTrue(toTest.isLoggedIn());
//		toTest.mimic("mim");
//		Assert.assertTrue(toTest.isLoggedIn());
//		toTest.logout();
//		Assert.assertTrue(toTest.isLoggedIn());
//		toTest.logout();
//		Assert.assertFalse(toTest.isLoggedIn());
//	}
//
//	/**
//	 * Test method for {@link models.user.LoginState#getIDs()}.
//	 */
//	@Test
//	public void testGetIDs() {
//		toTest = new LoginState("session");
//		toTest.login("test");
//		toTest.mimic("mim1");
//		toTest.mimic("mim2");
//		ArrayList<String> res = toTest.getIDs();
//		boolean b = true;
//		b |= res.size()==3;
//		b |= res.contains("test");
//		b |= res.contains("mim1");
//		b |= res.contains("mim2");
//		Assert.assertTrue(b);
//	}
//
//	/**
//	 * Test method for {@link models.user.LoginState#logout()}.
//	 */
//	@Test
//	public void testLogout() {
//		toTest = new LoginState("session");
//		assertFalse(toTest.logout());
//		toTest.login("test");
//		assertTrue(toTest.logout());
//		assertFalse(toTest.isLoggedIn());
//	}
//
//	/**
//	 * Test method for {@link models.user.LoginState#login(java.lang.String)}.
//	 */
//	@Test
//	public void testLogin() {
//		toTest = new LoginState("session");
//		Assert.assertTrue(toTest.login("test"));
//		Assert.assertTrue(toTest.isLoggedIn());
//		Assert.assertFalse(toTest.login("test2"));
//		Assert.assertEquals("test",toTest.getID());
//	}
//
//	/**
//	 * Test method for {@link models.user.LoginState#mimic(java.lang.String)}.
//	 */
//	@Test
//	public void testMimic() {
//		toTest = new LoginState("session");
//		Assert.assertFalse(toTest.mimic("mim"));
//		Assert.assertFalse(toTest.isLoggedIn());
//		toTest.login("test");
//		Assert.assertTrue(toTest.mimic("mim"));
//		Assert.assertEquals("mim", toTest.getID());
//		Assert.assertTrue(toTest.getIDs().contains("test"));
//	}
//
//}
