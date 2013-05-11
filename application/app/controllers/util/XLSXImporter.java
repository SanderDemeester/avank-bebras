package controllers.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Jens N. Rammant
 * Utility class for the reading and writing of XLSX files
 * TODO create jUnit test
 */

public class XLSXImporter {

    /**
     * sheet id to open
     */
    public static final int READSHEET = 0;
    /**
     * Sheet name to write to
     */
    public static final String WRITESHEET = "Sheet1";

    /**
     * Reads the XLSX file in an List of Lists of Strings. Each List of Strings represents
     * a row. An empty List represents an empty row. Each String represents a cell in that row.
     * An empty string represents a blank cell (or an error value).
     * @param f File to be read in
     * @return the content of the XLSX file
     * @throws IOException thrown if the file is not accessible or is not a valid XLSX file
     */
    public static List<List<String>> read(File f) throws IOException{
        List<List<String>> res = new ArrayList<List<String>>();

        //Read in the file and select the Sheet to read
        InputStream fileToRead = new FileInputStream(f);
        XSSFWorkbook wb = new XSSFWorkbook(fileToRead);
        XSSFSheet sheet = wb.getSheetAt(READSHEET);

        //Allocation of variables
        int rowNr = 0;
        int colNr = 0;
        XSSFRow r;
        XSSFCell c;

        //Iterate over rows
        Iterator<Row> rows = sheet.rowIterator();
        while(rows.hasNext()){
            List<String> list= new ArrayList<String>();
            r=(XSSFRow) rows.next();

            //Add empty Lists for empty rows
            for(int i=rowNr;i<r.getRowNum();i++) res.add(new ArrayList<String>());
            rowNr=r.getRowNum()+1;

            //Iterate over cells
            colNr = 0;
            Iterator<Cell> cells = r.cellIterator();
            while(cells.hasNext()){
                c=(XSSFCell) cells.next();

                //Add empty strings for empty cells
                for(int i=colNr;i<c.getColumnIndex();i++)list.add("");
                colNr=c.getColumnIndex()+1;
                String st;
                //Retrieve content of cell as string
                if(c.getCellType() != XSSFCell.CELL_TYPE_FORMULA)st=cellToString(c,c.getCellType());
                else st=cellToString(c, c.getCachedFormulaResultType());
                list.add(st);
            }
            res.add(list);
        }

        return res;
    }

    /**
     * Writes a List of Lists of Strings in an XSLX file. Conventions are the same as the
     * read conventions. Everything is written as a String.
     * @param toWrite Contents to be written in XSLX file
     * @param location Location where the file has to be written
     * @throws IOException when something goes wrong with the writing
     */
    public static void write(List<List<String>> toWrite, File location) throws IOException{
        //Create workbook & sheet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sh = wb.createSheet(WRITESHEET);

        //Create rows
        for(int r = 0;r<toWrite.size();r++){
            XSSFRow row = sh.createRow(r);
            //Fill row with cells
            for(int c=0;c<toWrite.get(r).size();c++){
                XSSFCell cell = row.createCell(c);
                cell.setCellValue(toWrite.get(r).get(c));
            }
        }

        //Write to output
        FileOutputStream fos = new FileOutputStream(location);
        wb.write(fos);
        fos.flush();
        fos.close();
    }
    /**
     *
     * @param c Cell to be read
     * @param cellType Type of the dereferenced (for Formulas) content of the cell
     * @return content of the cell as String
     */
    private static String cellToString(XSSFCell c, int cellType){
        if(cellType == XSSFCell.CELL_TYPE_BOOLEAN){
            return Boolean.toString(c.getBooleanCellValue());
        }
        if(cellType == XSSFCell.CELL_TYPE_NUMERIC){
        	if(DateUtil.isCellDateFormatted(c)){
        		return DateFormatter.formatDate(c.getDateCellValue());
        	}
            return Double.toString(c.getNumericCellValue());
        }
        if(cellType == XSSFCell.CELL_TYPE_STRING){
            return c.getStringCellValue();
        }

        return "";
    }

}