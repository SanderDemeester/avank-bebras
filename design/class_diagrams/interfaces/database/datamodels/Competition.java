
package datamodels;

import enums.ContestType;

/**
 *
 * @author Jens N. Rammant
 */
public class Competition implements Model{
    private String ID;
    private String name;
    private ContestType type;
    private boolean active;

    public Competition(String ID, String name, ContestType type, boolean active) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.active = active;
    }

    public boolean isActive() {
        return active;
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
