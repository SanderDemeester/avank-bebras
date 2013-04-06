package models.data;

/**
 * Represents a Difficulty that can be used in various QuestionSets
 * @author Ruben Taelman, Eddy Van Den Heuvel
 */
public class Difficulty {

    private String name;

    /**
     * Creates a new Difficulty based on the provided name.
     * @param name The name of the new Difficulty.
     */
    public Difficulty(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the difficulty.
     * @return The name of the difficulty
     */
    public String getName() {
        return name;
    }
}
