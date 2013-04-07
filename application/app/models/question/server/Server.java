package models.question.server;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.management.Editable;
import models.management.Listable;
import models.management.ManageableModel;
import play.data.validation.Constraints;

/**
 * ServerController entity managed by Ebean.
 *
 * @author Ruben Taelman
 * @author Kevin Stobbelaar
 *
 */
@Entity
@Table(name="servers")
public class Server extends ManageableModel implements Listable{

    // TODO database aanpassen zodat een server een unieke id krijgt, die niet de naam van de server is !

    @Editable(uponCreation=true)
    @Id
    @Column(name="id")
    public String id;

    @Editable
    @Column(name="location")
    @Constraints.Required
    public String path;
    
    @Editable
    @Constraints.Required
    public String ftpuri;
    
    @Editable
    @Constraints.Required
    public int ftpport;
    
    @Editable
    @Constraints.Required
    public String ftpuser;
    
    @Editable
    @Constraints.Required
    public String ftppass;
    
    public static Finder<String, Server> finder = new Finder<String, Server>(String.class, Server.class);

    public static Server findById(String name) {
        return finder.byId(name);
    }
    
    /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    @Override
    public String[] getFieldValues() {
        String[] result = {this.id, this.path};
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
    
    @Override
    public Map<String, String> options() {
        List<Server> servers = finder.all();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Server server: servers) {
            options.put(server.id, server.id);
        }
        return options;
    }

}
