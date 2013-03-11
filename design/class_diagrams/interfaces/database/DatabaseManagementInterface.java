
import datamodels.Model;
import java.util.Collection;
import queries.Query;



/**
 *
 * @author Jens N. Rammant
 * 
 * Regarding the creation of new users: to be able to be inserted in the database, the ID of the user
 * has to be unique over ALL the users of ALL types
 */
public interface DatabaseManagementInterface<T extends Model, Q extends Query<T>> {
        
    /*
     * Tries to insert all elements from c. Return the elements that could not be inserted
     */
    public Collection<T> insert(Collection<T> c);
    
    /*
     * Alters the elements in c, using their primary key (id). Returns those that were not there.
     */
    public Collection<T> alter(Collection<T> c);
    
    /*
     * Returns the T that comply with the restrictions set by query
     */
    public Collection<T> get(Q Query);
    
    /*
     * Deletes all the elements in c. Returns those that could not be deleted
     * (because they either weren't there or other elements were dependent on them).
     */
    public Collection<T> remove(Collection<T> c);
    
    
    
}
