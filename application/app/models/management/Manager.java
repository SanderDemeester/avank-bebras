package models.management;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import play.db.ebean.Model.Finder;
import play.mvc.Call;

import com.avaje.ebean.Page;

/**
 * Abstract class for every manager that contains the functionality for CRUD operations
 * on an entity.
 *
 * @author Kevin Stobbelaar, Ruben Taelman
 *
 */
public abstract class Manager<T extends ManageableModel> {

    private Finder<String, T> finder;
    
    protected int pageSize;
    protected String orderBy;
    protected String order;
    
    public static final int DEFAULTPAGESIZE = 10;
    public static final String DEFAULTORDER = "asc";
    
    protected Map<String, FieldType> fields = new LinkedHashMap<String, FieldType>();

    /**
     * Constructor for manager.
     *
     * @param finder finder object that helps building queries and returning pages.
     * @param pageSize number of elements displayed on one page
     */
    public Manager(Class modelClass){
        this.finder = new Finder<String, T>(String.class, modelClass);
        this.pageSize = DEFAULTPAGESIZE;
        this.order = DEFAULTORDER;
        
        // Add the necessary fields to the fields-map
        for(Field field : modelClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(Editable.class))
                fields.put(field.getName(), FieldType.getType(field.getType()));
        }
    }
    
    /**
     * Set the pagesize for pagination
     * @param pageSize new pagesize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * Set the default field on which the sorting should happen
     * @param orderBy order field
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    /**
     * Set the sorting order
     * @param order "asc" or "desc"
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Returns a page with elements of type T.
     *
     * WARNING: it's better to override this method in your own manager!
     *
     * @param page     page number
     * @param orderBy  attribute to sort on
     * @param order    sort order
     * @param filter   filter to select specific elements
     * @return the requested page
     */
    public Page<ManageableModel> page(int page, String filter) {
        return (Page<ManageableModel>) finder.where()
                // .ilike("name", "%" + filter + "%")
            .orderBy(orderBy + " " + order)
                // .fetch("path")
            .findPagingList(pageSize)
            .getPage(page);
    }

    /**
     * Returns the finder object for this manager.
     *
     * @return finder
     */
    public Finder<String, T> getFinder(){
        return finder;
    }

    /**
     * Returns the number of elements per page.
     * @return page size
     */
    public int getPageSize(){
        return pageSize;
    }

    /**
     * Returns the column headers for the objects of type T.
     *
     * @return column headers
     */
    public abstract String[] getColumnHeaders();

    /**
     * Returns the route that must be followed to refresh the list.
     *
     * @param page     current page number
     * @param orderBy  name of the column to sort on
     * @param order    ASC or DESC
     * @param filter   filter on the items
     * @return Call Route that must be followed
     */
    public abstract Call getListRoute(int page, String filter);
    
    public Call getListRoute() {
        return getListRoute(pageSize, "");
    }

    /**
     * Returns the path of the route that must be followed to create a new item.
     *
     * @return Call path of the route that must be followed
     */
    public abstract Call getAddRoute();

    /**
     * Returns the path of the route that must be followed to edit the selected item.
     *
     * @return Call path of the route that must be followed
     */
    public abstract Call getEditRoute(String id);

    /**
     * Returns the path of the route that must be followed to remove the selected item.
     *
     * @result Call path of the route that must be followed
     */
    public abstract Call getRemoveRoute(String id);
    
    /**
     * Returns the path of the route that must be followed to save the current item.
     * @return Call path of the route that must be followed
     */
    public abstract play.api.mvc.Call getSaveRoute();
    
    /**
     * Returns the path of the route that must be followed to update(save) the current item.
     * @return Call path of the route that must be followed
     */
    public abstract play.api.mvc.Call getUpdateRoute();
    
    /**
     * The fields this manager will show
     * @return map with field names and their type
     */
    public Map<String, FieldType> getFields() {
        return fields;
    }
    
    /**
     * Returns the prefix for translation messages.
     *
     * @return name
     */
    public abstract String getMessagesPrefix();
}
