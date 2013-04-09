package models.management;

import java.util.Map;

/**
 * This interface should be implemented into classes that are present as a field in a ManageableModel
 * and have different options.
 * For example a ManageableModel A that has an @Editable field of class B that implements Listable.
 * The generated form view will fetch the options() and show them as a dropdown list.
 * @author Ruben Taelman
 *
 */
public interface Listable {
    /**
     * The different options to be rendered in a dropdown list of the form.
     * @return A map with the first argument the value that will be present in the 'value'
     * attribute of the \<option\> tag. The second argument will be rendered as the textual value of
     * the option that will be visible to the user.
     */
    public Map<String, String> options();
}
