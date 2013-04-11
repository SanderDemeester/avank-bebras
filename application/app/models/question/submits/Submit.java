package models.question.submits;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.dbentities.UserModel;
import models.user.Author;
import models.user.User;
import play.Logger;
import play.Play;

/**
 * A model to hold submitted questions
 * @author Ruben Taelman
 *
 */
public class Submit implements Comparable<Submit>{
    
    private User user;
    private File file;
    
    /**
     * Create a new submitted question model
     * @param user the author of the question
     * @param file the location of the zipped question
     */
    public Submit(User user, File file) {
        this.user = user;
        this.file = file;
    }
    
    /**
     * The column values for the list table
     * @return values
     */
    public String[] getFieldValues() {
        SimpleDateFormat format = new SimpleDateFormat(Play.application().configuration().getString("application.dateFormat"));
        return new String[]{user.getData().name, format.format(file.lastModified())};
    }
    
    /**
     * The author id
     * @return the author id
     */
    public String getUserID() {
        return user.getID();
    }
    
    /**
     * The question file name
     * @return the file name
     */
    public String getFileName() {
        return file.getName();
    }
    
    /**
     * The question file
     * @return the file
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Delete the question file for this submission
     */
    public void delete() {
        file.delete();
    }
    
    /**
     * Find all the submissions
     * @param filter the string to filter with
     * @return a list of submissions
     */
    public static List<Submit> findAll(String filter) {
        List<Submit> list = new ArrayList<Submit>();
        
        String location = Play.application().configuration().getString("questioneditor.submit");
        File rootFolder = new File(location);
        if(rootFolder.exists() && rootFolder.isDirectory()) {
            for(File userFolder : rootFolder.listFiles()) {
                String userID = userFolder.getName();
                if(userFolder.exists() && userFolder.isDirectory()) {
                    for(File questionPack : userFolder.listFiles()) {
                        try {
                            UserModel model = UserModel.find.byId(userID);
                            if(model != null && model.name.contains(filter)) {
                                User user = new Author(UserModel.find.byId(userID));
                                list.add(new Submit(user, questionPack));
                            }
                        } catch (Exception e) {
                            // We just skip this question
                        }
                    }
                }
            }
        }
        
        // Default sorting with our own compareTo
        Collections.sort(list);
        
        return list;
    }
    
    /**
     * Find a submission
     * @param userID the userid for the wanted submission
     * @param fileName the filename for the wanted submission
     * @return the requested submission or null
     */
    public static Submit find(String userID, String fileName) {
        // Find file
        String location = Play.application().configuration().getString("questioneditor.submit")
                + "/" + userID.replace("/", "");// Extra hard check to deny access to other subfolders
        File file = new File(location, fileName);
        
        // Find user
        UserModel model = UserModel.find.byId(userID);
        
        if(file.exists() && model != null) return new Submit(new Author(model), file);
        else                               return null;
    }

    /**
     * Order by userID and file last modified
     */
    @Override
    public int compareTo(Submit o) {
        if(o.getUserID().equals(getUserID()))
            return Long.compare(this.getFile().lastModified(), o.getFile().lastModified());
        else
            return o.getUserID().compareTo(o.getUserID());
    }
}
