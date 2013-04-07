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
    boolean uponCreation() default false;
}