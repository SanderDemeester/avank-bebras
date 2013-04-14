/**
 * 
 */
package models.util;

import play.data.validation.Constraints;

/**
 * @author Jens N. Rammant
 * TODO comments
 */
public class IDWrapper{
	@Constraints.Required
	public String id;
}
