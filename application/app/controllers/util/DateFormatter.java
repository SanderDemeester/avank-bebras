/**
 * 
 */
package controllers.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jens N. Rammant
 *
 */
public class DateFormatter {
	
	public static final String FORMAT = "dd/MM/yyyy";

	public static String formatDate(Date d){
		Format formatter = new SimpleDateFormat(FORMAT);
		return formatter.format(d);
	}
}
