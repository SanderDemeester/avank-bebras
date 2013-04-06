package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import models.management.Editable;
import models.management.ManageableModel;
import models.question.server.Server;
import models.user.Author;

/**
 * The model for questions that will be saved and fetched from the database
 * @author Ruben Taelman
 *
 */

@Entity
@SequenceGenerator(name="Seq", sequenceName="questions_id_seq")
@Table(name="questions")
public class QuestionModel extends ManageableModel{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq")//@GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    
    @Editable
    public String officialid;
    @Editable
    public Server server;
    @Editable
    public String path;
    @Editable
    public boolean active;
    @Editable
    public Author author;
    
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
        return Integer.toString(id);
    }
}
