package models.question.server;

import models.management.ManageableModel;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * ServerController entity managed by Ebean.
 *
 * @author Ruben Taelman
 * @author Kevin Stobbelaar
 *
 */
@Entity
@Table(name="servers")
public class Server extends ManageableModel {

    // TODO database aanpassen zodat een server een unieke id krijgt, die niet de naam van de server is !

    @Id
    @Column(name="id")
    public String name;

    @Column(name="location")
    @Constraints.Required
    public String path;
    
    @Constraints.Required
    public String ftpuri;
    
    @Constraints.Required
    public int ftpport;
    
    @Constraints.Required
    public String ftpuser;
    
    @Constraints.Required
    public String ftppass;

    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {this.name, this.path};
        return result;
    }

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    @Override
    public String getID() {
        return name;
    }

}
