/**
 *
 */
package models.util;

import play.data.validation.Constraints;

/**
 * @author Jens N. Rammant
 * A class to be used for forms which only have 1 field: id
 */
public class IDWrapper{
    @Constraints.Required
    public String id;
}
