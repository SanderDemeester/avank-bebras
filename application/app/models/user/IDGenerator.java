package models.user;

import java.text.Normalizer;

import java.lang.Character;
import java.lang.CharSequence;
import java.lang.StringBuilder;

import java.util.Set;
import java.util.TreeSet;
import java.util.Date;
import java.util.Arrays;

/**
 * This class generates Bebras User Identifiers based on the provided user data.
 * It takes mostly the name into account, but other data might be used in case
 * of clashes.
 * @author Felix Van der Jeugt
 */
public class IDGenerator {

    /** The size of the generated usernames. Eight should be plenty. */
    public static final int SIZE = 8;

    /* The characters that can seperate two names. */
    private static Set<Character> seperators = new TreeSet<Character>(
        Arrays.asList(' ', '-')
    );

    /**
     * Generates a new Identifier based on the user's real name and birthday.
     * @param name The user's real name
     * @param birthday The user's day of birth
     * @return The user's new and unique Bebras ID.
     */
    public static String generate(String name, Date birthday) {
        name = cleanName(name);
        return name;
    }

    public static String cleanName(String name) {
        CharSequence comp = Normalizer.normalize(name, Normalizer.Form.NFKD);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < comp.length(); i++) {
            if('A' <= comp.charAt(i) && comp.charAt(i) <= 'Z'
            || 'a' <= comp.charAt(i) && comp.charAt(i) <= 'z') {
                builder.append(comp.charAt(i));
            }
            if(seperators.contains(comp.charAt(i))) builder.append(' ');
        }
        return builder.toString().toLowerCase();
    }

}
