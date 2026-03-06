package steve;

/**
 * Represents a basic todo task with no associated date or time.
 * A Todo is the simplest form of task — just a description and a done status.
 * It is displayed with the {@code [T]} prefix.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description A non-empty string describing what needs to be done.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a human-readable string representation of this Todo,
     * prefixed with {@code [T]}.
     *
     * @return A formatted string such as {@code [T][ ] buy groceries}.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a pipe-delimited string representation of this Todo
     * suitable for saving to a file.
     *
     * @return A string in the format {@code T | isDone | description}.
     */
    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }
}