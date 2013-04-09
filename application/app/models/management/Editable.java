package models.management;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to tag fields inside a ManageableModel to allow these fields to be edited
 * from within a view.
 * @author Ruben Taelman
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
public @interface Editable {
    /**
     * An optional argument to tell the default form that this field only should
     * be shown (and be editable) when the Manager in om state ModelState.CREATE
     * This could be used for user-generated id-fields.
     * @return if the field should only be editable upon creation of a model
     */
    boolean uponCreation() default false;
}