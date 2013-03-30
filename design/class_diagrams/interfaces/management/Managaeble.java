public interface Manageable {
	
	 /**
     * Returns those values that have to be represented in a table.
     *
     * @return array with the current values of the fields to be represented in the table
     */
    public String[] getFieldValues();

    /**
     * Returns the id of the object.
     *
     * @return id
     */
    public String getID();

}