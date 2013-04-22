package controllers.util;

public class PasswordHasher {
	
	/**
	 * 
	 * @param password the plaintext password
	 * @return a container containing the fully hashed & hexed password and the hexed hash. Ready to be put in the
	 * 			database
	 */
	public static HashAndPassword hashPassword(String password){
		//TODO implement
		HashAndPassword res = new HashAndPassword();
		res.hash="hash";
		res.password=password;
		return res;
	}

	/**
	 * Wrapper class for hashed password and hash
	 * @author Jens N. Rammant
	 *
	 */
	public static class HashAndPassword{
		public String hash;
		public String password;
	}
}
