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

import models.user.UserID;

import org.w3c.dom.Document;

import play.Play;
import scala.actors.threadpool.Arrays;

public class QuestionPack {
    private Document document;
    private List<File> indexes = new LinkedList<File>();
    private List<File> feedbacks = new LinkedList<File>();
    
    private String tempDownloadLocation;
    private String hash;
    private String userDownloadLocation;
    
    private static final String INDEXFILENAME = "index_LANG.html";
    private static final String FEEDBACKFILENAME = "feedback_LANG.html";
    private static final String QUESTIONZIPFILE = "question.zip";
    private static final String QUESTIONXMLFILE = "question.xml";
    
    public QuestionPack(Document document) throws IOException {
        this.document = document;
    }
    
    public void setTempDownloadLocation(String tempDownloadLocation) {
        this.tempDownloadLocation = tempDownloadLocation;
    }
    
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public void setUserDownloadLocation(String userDownloadLocation) {
        this.userDownloadLocation = userDownloadLocation;
    }
    
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
    
    public String addIndex(String lang, String contents) throws IOException {
        String name = INDEXFILENAME.replaceAll("LANG", lang);
        this.indexes.add(writeStringToFile(name, contents));
        return name;
    }
    
    public String addFeedback(String lang, String contents) throws IOException {
        String name = FEEDBACKFILENAME.replaceAll("LANG", lang);
        this.feedbacks.add(writeStringToFile(name, contents));
        return name;
    }
    
    private static void addToZip(ZipOutputStream zout, String name, File file) throws IOException {
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
    
    private static void addToZip(ZipOutputStream zout, Iterable<File> files) throws IOException {
        for(File file : files) {
            String newName = file.getName().replaceAll("~[^.]*$", "");
            addToZip(zout, newName, file);
        }
    }
    
    public File export(UserID userID) {
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
            addToZip(zout, Arrays.asList(folder.listFiles()));
            
            zout.close();
            
            return zipFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Invalid file.", e);
        } catch (IOException e) {
            throw new RuntimeException("IO error.", e);
        }
    }
    
    public List<File> getIndexes() {
        return this.indexes;
    }
    
    public List<File> getFeedbacks() {
        return this.feedbacks;
    }
    
    public Document getXmlDocument() {
        return this.document;
    }
    
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
