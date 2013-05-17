/**
 *
 */
package controllers.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jens N. Rammant
 *
 */
public class DateFormatter {

    public static final String FORMAT = "dd/MM/yyyy";
    public static final String FORMAT_EXTENDED = "HH:MM dd/MM/yyyy";

    public static String formatDate(Date d){
        return formatDate(d, false);
    }

    public static String formatDate(Date d, boolean extended){
        if(d==null)return null;
        Format formatter = new SimpleDateFormat(extended?FORMAT_EXTENDED:FORMAT);
        return formatter.format(d);
    }

    public static Date parseString(String st){
        if(st==null)return null;
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        try {
            return formatter.parse(st);
        } catch (ParseException e) {
            return null;
        }
    }
}
