/**
 * 
 */
package models.user;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.PersistenceException;

import play.i18n.Lang;

import com.avaje.ebean.Ebean;

import controllers.util.InputChecker;

import models.dbentities.UserModel;

/**
 * @author Jens N. Rammant
 *
 */
public class UserModelValidator {
	/**
	 * Checks if the basic data in the usermodel is valid. 
	 * Does not check if there is an emailaddress. If there is one, it WILL check if it's valid
	 * @param toValidate model to validate
	 * @return a result that gives some more info on what is wrong (if anything)
	 * @throws PersistenceException
	 */
	public static Result validate(UserModel toValidate) throws PersistenceException{
		//Check name for illegal characters
		if(toValidate.name==null||toValidate.name.isEmpty())return Result.NO_NAME;
		//TODO illegal characters (if any)
		//Check if registrationdate is before today
		if(toValidate.birthdate==null)return Result.NO_BIRTHDAY;
		Date currentDate = Calendar.getInstance().getTime();
		if(toValidate.birthdate.after(currentDate))return Result.BIRTHDAY_AFTER_TODAY;
		//Check if there is a Gender and preferred language
		if(toValidate.gender==null)return Result.NO_GENDER;
		if(toValidate.preflanguage==null)return Result.NO_LANGUAGE;
		try{
			Lang lan = Lang.forCode(toValidate.preflanguage);
			if(!Lang.availables().contains(lan))return Result.INVALID_LANGUAGE;
		}catch(Exception e){
			return Result.INVALID_LANGUAGE;
		}
		//Check password
		if(toValidate.password==null||toValidate.password.isEmpty())return Result.NO_PASSWORD;
		//Check email, if any
		if(toValidate.email!=null){
			if(!InputChecker.getInstance().isCorrectEmail(toValidate.email))return Result.INVALID_EMAIL;
			UserModel doubleEmail = Ebean.find(UserModel.class).where().eq("email", toValidate.email).findUnique();
			if(doubleEmail!=null&&!doubleEmail.id.equals(toValidate.id))return Result.ALREADY_EXISTING_EMAIL;
		}
		return Result.OK;
		
		
	}
	
	public enum Result{
		OK,
		NO_NAME,
		INVALID_NAME,
		NO_BIRTHDAY,
		BIRTHDAY_AFTER_TODAY,
		NO_GENDER,
		NO_LANGUAGE,
		INVALID_LANGUAGE,
		NO_PASSWORD,
		INVALID_EMAIL,
		ALREADY_EXISTING_EMAIL;
	}
}
