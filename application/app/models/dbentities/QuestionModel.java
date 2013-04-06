package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.Manageable;
import models.question.server.Server;
import models.user.Author;
import play.db.ebean.Model;

/**
 * The model for questions that will be saved and fetched from the database
 * @author Ruben Taelman
 *
 */

@Entity
@Table(name="questions")
public class QuestionModel extends Model implements Manageable{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    
    public String officialid;
    public Server server;
    public String path;
    public boolean active;
    public Author author;
    
    // TMP!!!
    public QuestionModel() {
        //this.id="a";
        this.officialid="iodtje";
        this.path="padje";
    }
    
    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {this.officialid
                , this.server.name
                , this.path
                , Boolean.toString(this.active)
                , this.author.getData().name};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return id;
    }
}
