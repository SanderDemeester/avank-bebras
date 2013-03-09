public interface Manager<T> {

	public List<T> getItems();
	public void createItem(T item);
	public void updateItem(T item);
	public void removeItem(T item);
	public String displayItem(T item);
	public void setItemsPerPage(int itemsPerPage);
	public void showNextPage();
	public void showPrevPage();
	public int getCurrentPage();

}