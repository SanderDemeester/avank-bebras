package models.question;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * A wrapper class for the questions before they will be downloaded
 * @author Ruben Taelman
 *
 */

public class QuestionPack {
    private Document document;
    private List<File> indexes = new LinkedList<File>();
    private List<File> feedbacks = new LinkedList<File>();

    private String tempDownloadLocation;
    private String hash;
    private String userDownloadLocation;

    public static final String INDEXFILENAME = "index_LANG.html";
    public static final String FEEDBACKFILENAME = "feedback_LANG.html";
    public static final String QUESTIONZIPFILE = "question.zip";
    public static final String QUESTIONXMLFILE = "question.xml";

    /**
     * Make a new questionpack with a document-based question as input
     * @param document xml-formatted question
     */
    public QuestionPack(Document document) {
        this.document = document;
    }

    /**
     * Set the temporary download location for this pack
     * @param tempDownloadLocation Where the user can temporarily download this pack
     */
    public void setTempDownloadLocation(String tempDownloadLocation) {
        this.tempDownloadLocation = tempDownloadLocation;
    }

    /**
     * Set a hash that can be used in this pack
     * @param hash a chosen hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Set the download location for this pack resource files
     * @param userDownloadLocation The http-accessible resource location for the current user
     */
    public void setUserDownloadLocation(String userDownloadLocation) {
        this.userDownloadLocation = userDownloadLocation;
    }

    /**
     * Write a file with some content to a location and replace all the src attributes in html to
     * remove the userDownloadLocation
     * @param name the name of the file
     * @param contents the contents to be written
     * @return a newly created file
     * @throws IOException if a problem occured with the File IO
     */
    private File writeStringToFile(String name, String contents) throws IOException {
        // Remove all user ref's
        contents = contents.replaceAll("src=\""+userDownloadLocation+"/", "src=\"");

        // Write to file
        File file = QuestionIO.addTempFile(tempDownloadLocation, name+"~"+hash);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(contents);
        out.close();
        return file;
    }

    /**
     * Add the content from an index page from a certain language
     * @param lang language code
     * @param contents content of the page
     * @return the name of the file that will be created for the index page
     * @throws IOException
     */
    public String addIndex(String lang, String contents) throws IOException {
        String name = INDEXFILENAME.replaceAll("LANG", lang);
        this.indexes.add(writeStringToFile(name, contents));
        return name;
    }

    /**
     * Add the content from an feedback page from a certain language
     * @param lang language code
     * @param contents content of the page
     * @return the name of the file that will be created for the feedback page
     * @throws IOException
     */
    public String addFeedback(String lang, String contents) throws IOException {
        String name = FEEDBACKFILENAME.replaceAll("LANG", lang);
        this.feedbacks.add(writeStringToFile(name, contents));
        return name;
    }

    /**
     * Add a file with a certain name to a zip file
     * @param zout outputstream for the zip file
     * @param name the name the file will get inside the zip file
     * @param file the file that will be added to the zip file
     * @throws IOException if a problem occured while writing to the zip file
     */
    public static void addToZip(ZipOutputStream zout, String name, File file) throws IOException {
        ZipEntry ze = new ZipEntry(name);
        zout.putNextEntry(ze);
        byte[] buffer = new byte[1024];
        FileInputStream in = new FileInputStream(file);
        int len;
        while ((len = in.read(buffer)) > 0) {
            zout.write(buffer, 0, len);
        }

        in.close();

        zout.closeEntry();
    }

    /**
     * Add multiple files to a zip file that were temporarily saved on the server
     * @param zout outputstream for the zip file
     * @param files files to be added to the zip archive
     * @throws IOException if a problem occured while writing to the zip file
     */
    public static void addToZip(ZipOutputStream zout, Iterable<File> files) throws IOException {
        for(File file : files) {
            String newName = file.getName().replaceAll("~[^.]*$", "");
            addToZip(zout, newName, file);
        }
    }

    /**
     * Export this questionpack to a zip archive
     * @param userID the id of the current user
     * @return a zip archive of this question pack
     */
    public File export(String userID) {
        try {
            File zipFile = QuestionIO.addTempFile(tempDownloadLocation, QUESTIONZIPFILE+"~"+hash);

            FileOutputStream fout = new FileOutputStream(zipFile);
            ZipOutputStream zout = new ZipOutputStream(fout);

            // Add xml file to zip
            addToZip(zout, "question.xml", getXmlFile());

            // Add indexes to zip
            addToZip(zout, indexes);

            // Add feedbacks to zip
            addToZip(zout, feedbacks);

            // Add uploaded files to zip
            String location = QuestionIO.getUserUploadLocation(userID);
            File folder = new File(location);
            List<File> list = new LinkedList<File>();
            for(File f : folder.listFiles()) list.add(f);
            addToZip(zout, list);

            zout.close();

            return zipFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Invalid file.", e);
        } catch (IOException e) {
            throw new RuntimeException("IO error.", e);
        }
    }

    /**
     * Add this question pack to the submit folder of this question author to await approval
     * @param userID id of the author
     */
    public void submit(String userID) {
        File zip = export(userID);
        File submit = new File(QuestionIO.getUserSubmitLocation(userID), QUESTIONZIPFILE+"~"+hash);
        zip.renameTo(submit);
    }

    /**
     * Get a list of the temporary index files
     * @return temporary index files
     */
    public List<File> getIndexes() {
        return this.indexes;
    }

    /**
     * Get a list of the temporary feedback files
     * @return temporary feedback files
     */
    public List<File> getFeedbacks() {
        return this.feedbacks;
    }

    /**
     * The document file for the question this pack contains
     * @return xml-formatted question
     */
    public Document getXmlDocument() {
        return this.document;
    }

    /**
     * Make a new temporary xml file for the question inside this pack
     * @return a file that contains the xml
     */
    public File getXmlFile() {
        try {
            String xmlfile = QUESTIONXMLFILE+"~"+hash;

            Source source = new DOMSource(document);

            // Prepare the xml output file
            File file = QuestionIO.addTempFile(tempDownloadLocation, xmlfile);
            Result result = new StreamResult(file);

            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            xformer.setOutputProperty(OutputKeys.METHOD, "xml");
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            xformer.transform(source, result);

            return file;
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException("Transformation server error.", e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new RuntimeException("Transformation server error.", e);
        } catch (TransformerException e) {
            throw new RuntimeException("Transformation error.", e);
        }
    }
}
