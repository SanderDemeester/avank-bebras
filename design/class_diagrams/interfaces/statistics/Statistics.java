
package statictics;

import play.mvc.Result;

import user.User;

/**
 * Interface to the Statistics subsystem.
 *
 * This interface is responsible for showing the statistics page to the user. As
 * all possible requests for statistics are build into this page, this interface
 * will be the only contact point for other subsystems with this one.
 *
 * @author Felix Van der Jeugt
 */

public interface Statistics {

    /**
     * Displays the statistics starting page for this user.
     *
     * Depending on the kind of user, the shown chart varies. For a independent
     * user, it will show his personal score distribution. For a pupil, his
     * current class' score distribution. For a teacher, a comparison of his
     * classes. For an organizer, none.
     */
    public Result showStatistics(User user);

}

