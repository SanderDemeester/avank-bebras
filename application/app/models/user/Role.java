package models.user;

import java.util.List;
import java.util.Arrays;

import models.EMessages;
import models.data.Link;

/**
 * A class with all possible roles a user can have. These roles will also
 * influence the links on a user's landing page.
 * @author Ruben Taelman
 * @author Felix Van der Jeugt
 */
public class Role {


    /* ====================================================================== *\
                               Role definitions.
    \* ====================================================================== */

    // Mimicing
    public static Role MIMIC = new Role("Mimick users.", new Link[0]);

    // Anon
    public static Role LOGIN = new Role();
    public static Role REGISTER = new Role();

    // Authenticated
    public static Role LANDINGPAGE = new Role();
    public static Role CHANGEPASSWORD = new Role(
        "Change your password",
        new Link("Change password", "/passwedit")
    );

    // Organiser
    public static Role MANAGEQUESTIONS = new Role(
        "Manage the Questions",
        new Link("View questions", "/questions"),
        new Link("View submitted questions", "/questionsubmits")
    );
    public static Role MANAGESERVERS = new Role(
        "Manage the Question servers",
        new Link("View servers", "/servers"),
        new Link("New server", "/server/new")
    );

    // Author
    public static Role QUESTIONEDITOR = new Role(
        "Create and modify Questions",
        new Link("Question Editor", "/questioneditor")
    );

    //ADMIN
    public static Role MANAGEFAQ = new Role(
        "Manage Frequently Asked Questions",
        new Link("View the frequently asked questions", "/faq"),
        new Link("Manage the frequently asked questions", "/manageFAQ"),
        new Link("Create a new frequently asked question", "/manageFAQ/new")
    );
    public static Role DATAMANAGER = new Role(
        "Manipulate the database",
        new Link("Manage navigation bar links", "/manage/links/show"),
        new Link("Manage the difficulty levels", "/manage/difficulties/show"),
        new Link("Manage the student grades", "/manage/grades/show")
    );



    /* ====================================================================== *\
                             Actual implementation.
    \* ====================================================================== */

    private boolean landing;
    private String mtitle;
    private List<Link> pages;

    /**
     * Create a new Role which gives no links on the landing page.
     */
    private Role() {
        this.landing = false;
        this.mtitle  = null;
        this.pages   = null;
    }

    /**
     * Create a new Role.
     * @param mtitle The title for this role, as a EMessage string.
     * @param pages The pages this roles gives access to.
     */
    private Role(String mtitle, Link... pages) {
        this(mtitle, Arrays.asList(pages));
    }

    /**
     * @see models.user.Role.Role
     */
    private Role(String mtitle, List<Link> pages) {
        this.landing = true;
        this.mtitle  = mtitle;
        this.pages   = pages;
    }

    /**
     * Returns whetter this Role should show up on the landing page of the user.
     * @return On the landing page?
     */
    public boolean onLandingPage() {
        return landing;
    }

    /**
     * Returns the title as a translated human readable string.
     * @return The role's title.
     */
    public String title() {
        return EMessages.get(mtitle);
    }

    /**
     * Returns links to the pages this Role gives access to.
     * @return Accessable pages with this Role.
     */
    public List<Link> pages() {
        return pages;
    }

}
