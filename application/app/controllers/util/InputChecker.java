package controllers.util;

import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.user.AuthenticationManager;
import models.user.User;

/*
 * author: Thomas Mortier
 *
 * Description: InputChecker is used to check different kinds of inputstrings
 * like E-Mail or Name. You can simply add a new checker by adding a method public
 * boolean myChecker(String input){...}
 *
 */

public class InputChecker {

    private static InputChecker _instance = null;

    /* below you can define patterns */
    //BEGIN
    private static final String EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //END
    private InputChecker(){
        /* default */
    }

    public static InputChecker getInstance() {
        if(_instance==null)
            _instance = new InputChecker();
        return _instance;
    }

    public boolean isCorrectEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }
}
