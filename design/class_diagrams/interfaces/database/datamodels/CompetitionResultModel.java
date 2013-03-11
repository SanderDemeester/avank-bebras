
package datamodels;

/**
 *
 * @author Jens N. Rammant
 * This class is a bit special. If the dirty flag is true, then this will be added to the
 * ('dirty') H2 database, and all usual operations can be done. However, as soon as something is added 
 * with a false dirty flag, the relevant data is written to the real database and the record is
 * removed from the temporary H2.
 */
public class CompetitionResultModel implements Model{
    private String id;
    private boolean dirty;
    private CompetitionResult cr;

    public CompetitionResultModel(String id, boolean dirty, CompetitionResult cr) {
        this.id = id;
        this.dirty = dirty;
        this.cr = cr;
    }

    public String getId() {
        return id;
    }

    public boolean isDirty() {
        return dirty;
    }

    public CompetitionResult getCr() {
        return cr;
    }
    
    
}
