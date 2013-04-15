package models.dbentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import models.management.Editable;
import models.management.ManageableModel;
import models.question.Server;
import play.data.validation.Constraints;

import com.avaje.ebean.validation.NotNull;

/**
 * The model for questions that will be saved and fetched from the database
 * @author Ruben Taelman
 *
 */

@Entity
@SequenceGenerator(name="Seq", sequenceName="questions_id_seq")
@Table(name="questions")
public class QuestionModel extends ManageableModel{
    private static final long serialVersionUID = 2L;

    @Id
    @Editable(alwaysHidden=true, hiddenInList=true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq")//@GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Editable(uponCreation=true)
    @Constraints.Required
    public String officialid;

    @Editable
    @ManyToOne
    @NotNull
    @JoinColumn(name="serverid")
    public Server server;

    @Transient
    public String path;

    @Editable
    @Constraints.Required
    public boolean active;

    @Editable
    @ManyToOne
    @NotNull
    @JoinColumn(name="author")
    public UserModel author;
    
    /**
     * Constructor to create an empty model
     * @param user the author model
     * @param path the temp file name
     */
    public QuestionModel(UserModel user, String path) {
        this.author = user;
        this.path = path;
    }

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {this.officialid
                , this.server.id
                , this.path
                , Boolean.toString(this.active)
                , this.author.name};
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

    /**
     * Set the correct server field by the already saved server.id
     */
    public void fixServer() {
        if(this.server.id == null) {
            this.server = null;
        } else {
            this.server = Server.findById(server.id);
        }
    }

    /**
     * Insert this new question
     */
    @Override
    public void save() {
        fixServer();
        super.save();
    }
    
    /**
     * Check if the officialid in this model is not yet present in the database
     * @return is the officialid unique?
     */
    public boolean isUnique() {
        Finder<String, QuestionModel> finder = new Finder<String, QuestionModel>(String.class, QuestionModel.class);
        return finder.where().ilike("officialid", officialid).findList().isEmpty();
    }
}
