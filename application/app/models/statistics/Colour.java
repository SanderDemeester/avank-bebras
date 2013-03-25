package models.statistics;

import java.util.List;
import java.util.ArrayList;

/**
 * Models a colour by it's human readable name and css colour value. This
 * colour value is any String readable by css as a color (e.g. #FFF , white,
 * ...).
 * @author Felix Van der Jeugt
 */
public class Colour {

    private String name;
    private String html;

    /** Create a new Colour with the given name and html. */
    public Colour(String name, String html) {
        this.name = name;
        this.html = html;
    }

    public String getName() { return name; }
    public String getHtml() { return html; }

    // TODO Add each of these colours to the messages as "colours.name".
    public static Colour RED     = new Colour("red",     "red");
    public static Colour MAGENTA = new Colour("magenta", "#ff00ff");
    public static Colour BLUE    = new Colour("blue",    "blue");
    public static Colour CYAN    = new Colour("cyan",    "#00ffff");
    public static Colour GREEN   = new Colour("green",   "green");
    public static Colour YELLOW  = new Colour("yellow",  "#ffff00");

    private static List<Colour> colours = new ArrayList<Colour>();
    static {
        colours.add(RED);
        colours.add(MAGENTA);
        colours.add(BLUE);
        colours.add(CYAN);
        colours.add(GREEN);
        colours.add(YELLOW);
    }

    public static List<Colour> colours() {
        return colours;
    }

}

