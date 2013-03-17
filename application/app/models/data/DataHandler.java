
package models.data;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;

import models.data.FAQ;
import models.data.Grade;

/**
 * Stub class for DataHandler.
 * right now the data is hardcoded, this later be replaced by database data.
 *
 * @author Felix Van der Jeugt
 */
public class DataHandler {

    private static Grade[] grades = new Grade[] {
        new Grade("Ewok",    10, 12),
        new Grade("Wooky",   12, 14),
        new Grade("Padawan", 14, 16),
        new Grade("Jedi",    16, 18)
    };

    private static String[] difficulties = new String[] {
        "Easy",
        "Medium",
        "Hard"
    };

    private static List<Link> links = new ArrayList<Link>(2);
    static {

        links.add(new Link("Home", "http://www.bebras.be"));
        links.add(new Link("FAQ",  "file://FAQ.html"));
    };

    private static FAQ[] faqs = new FAQ[] {
        new FAQ("How do I login?", "Click the login button.")
    };

    /**
     * Returns the different Grades users can belong to.
     * The grades are sorted by ascending age.
     * @return Array of grades.
     */
    public static Grade[] getGrades() {
        return grades;
    }

    /**
     * Returns the different difficulties a question can have.
     * The difficulties are sorted by ascending difficulty.
     * @return Array of difficulties.
     */
    public static String[] getDifficulties() {
        return difficulties;
    }

    /**
     * Returns the links to show on the home page.
     * @return Links for the home page.
     */
    public static List<Link> getLinks() {
        return links;
    }

    /**
     * Returns the Frequently Asked Questions.
     * @return Frequently Asked Questions.
     */
    public static FAQ[] getFaqs() {
        return faqs;
    }

}
