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
    
    /**
     * default date format
     */
    public static final String FORMAT = "dd/MM/yyyy";
    /**
     * default extended date format
     */
    public static final String FORMAT_EXTENDED = "HH:MM dd/MM/yyyy";

    /**
     * Format a date to a string
     * @param d date
     * @return formated date in a string
     */
    public static String formatDate(Date d){
        return formatDate(d, false);
    }
    
    /**
     * Format a date to a string
     * @param d date
     * @param extended if the format should be extended
     * @return formated date in a string
     */
    public static String formatDate(Date d, boolean extended){
        if(d==null)return null;
        Format formatter = new SimpleDateFormat(extended?FORMAT_EXTENDED:FORMAT);
        return formatter.format(d);
    }
    
    /**
     * Parse a string to a date
     * @param st string representation of a date
     * @return date
     */
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
