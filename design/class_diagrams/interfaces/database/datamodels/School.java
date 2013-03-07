
package datamodels;

/**
 *
 * @author Jens N. Rammant
 */
public class School {
    private String id;
    private String name;
    private String address;

    public School(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    
    
}
