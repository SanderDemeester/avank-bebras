
package datamodels;

/**
 *
 * @author Jens N. Rammant
 */
public class Server {
    private String id;
    private String address;

    public Server(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
    
    
}
