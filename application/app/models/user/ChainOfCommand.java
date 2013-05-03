/**
 * 
 */
package models.user;

import javax.persistence.PersistenceException;

import models.dbentities.UserModel;

import com.avaje.ebean.Ebean;

/**
 * @author Jens N. Rammant
 *TODO tests
 */
public class ChainOfCommand {

	/**
	 * Checks whether the currently logged-in user is the superior of the
	 * user identified by 'inferior'
	 * @param inferior of the user to be checked
	 * @return whether current user is superior
	 * @throws PersistenceException
	 */
	public static boolean isSuperiorOf(String inferior) throws PersistenceException{
		UserModel inf = Ebean.find(UserModel.class, inferior);
		//checked on database TYPE entry, not java class
		return isSuperiorOf(new Independent(inf));
	}
	/**
	 * Checks whether the user identified by 'superior' is the superior of the user
	 *  identified by 'inferior'
	 * @param superior ID of the superior
	 * @param inferior ID of the inferior
	 * @return whether superior IS the superior of inferior
	 * @throws PersistenceException
	 */
	public static boolean isSuperiorOf(String superior,String inferior) throws PersistenceException{
		UserModel inf = Ebean.find(UserModel.class, inferior);
		UserModel sup = Ebean.find(UserModel.class, superior);
		//checked on database TYPE entry, not java class
		return isSuperiorOf(new Independent(sup),new Independent(inf));
	}
	
	/**
	 * Checks whether the currently logged-in user is the superior of the
	 * user identified by 'inferior'
	 * @param inferior the inferior user
	 * @return whether current user is superior
	 * @throws PersistenceException
	 */
	public static boolean isSuperiorOf(User inferior) throws PersistenceException{
		return isSuperiorOf(AuthenticationManager.getInstance().getCurrentMimickUser(), inferior);
	}
	
	/**
	 * Checks whether the user identified by 'superior' is the superior of the user
	 *  identified by 'inferior'
	 * @param superior the superior user
	 * @param inferior the inferior user
	 * @return whether superior IS the superior of inferior
	 */
	public static boolean isSuperiorOf(User superior, User inferior){
		if(!(inferior instanceof Authenticated)) return false;
		UserType sup = superior.data.type;
		UserType inf = superior.data.type;
		
		if(sup.equals(UserType.ADMINISTRATOR))return !inf.equals(UserType.ADMINISTRATOR);
		if(sup.equals(UserType.ORGANIZER))return !(inf.equals(UserType.ADMINISTRATOR)
				||inf.equals(UserType.ORGANIZER));
		if(sup.equals(UserType.TEACHER)&&inf.equals(UserType.PUPIL_OR_INDEP)){
			Teacher t = (Teacher) superior;
			return t.isPupilsTeacher(inferior);
		}
		return false;
	}
}
