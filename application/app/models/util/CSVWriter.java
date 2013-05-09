package util;

import java.lang.StringBuffer;
import java.lang.String;

/**
 * Helper class for writing Comma Seperated Values.
 * @author Felix Van der Jeugt
 */
public class CSVWriter {

    private StringBuffer buffer;

    public CSVWriter() {
        buffer = new StringBuffer();
    }

    public void addLine(String... values) {
        for(int i = 0; i < values.length - 1; i++) {
            buffer.append(values[i]);
            buffer.append(',');
        }
        if(values.length > 0) {
            buffer.append(values[values.length - 1]);
            buffer.append('\n');
        }
    }

    public String asString() {
        return buffer.toString();
    }

}
