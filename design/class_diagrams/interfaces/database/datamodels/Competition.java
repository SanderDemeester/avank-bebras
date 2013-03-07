
package datamodels;

import enums.ContestType;

/**
 *
 * @author Jens N. Rammant
 */
public class Competition {
    private String ID;
    private String name;
    private ContestType type;

    public Competition(String ID, String name, ContestType type) {
        this.ID = ID;
        this.name = name;
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ContestType getType() {
        return type;
    }
    
    
}
