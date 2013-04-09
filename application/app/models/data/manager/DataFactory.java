package models.data.manager;

/**
 * A factory for DataElements.
 * @author Felix Van der Jeugt
 */
public interface DataFactory<T extends DataElement> {

    /**
     * Returns the number of strings expected by the createfromStrings method.
     */
    public int stringsExpected();

    /**
     * Creates a new object from the Strings supplied.
     * @return The factorized object.
     */
    public T createFromStrings(String... strings);

}
