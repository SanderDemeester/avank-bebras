public abstract class Manager<T extends Manageable> {

	public Page<T> page(int page, int pageSize, String sortBy, String order, String filter){}

	public Finder<String, T> getFinder(){}

	public int getPageSize(){}

	public abstract String[] getColumnHeaders();

	public abstract Call getListRoute(int page, String orderBy, String order, String filter);

	public abstract Call getAddRoute();

	public abstract Call getEditRoute(String id);

	public abstract Call getRemoveRoute(String id);

}
