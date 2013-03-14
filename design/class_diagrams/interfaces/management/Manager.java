public interface Manager<T> {

	public Page<T> page(int page, int pageSize, String sortBy, String order, String filter);

}
