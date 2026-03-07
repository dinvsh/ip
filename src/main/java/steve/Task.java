package steve;

/**
 * Represents a generic task with a description and a completion status.
 * This is the base class for all task types (Todo, Deadline, Event).
 * Subclasses should override {@link #toString()} and {@link #toFileString()}
 * to include type-specific formatting.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initially not marked as done.
     *
     * @param description A non-empty string describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if the task is done, " " (space) otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a human-readable string representation of this task,
     * including its status icon and description.
     *
     * @return A formatted string such as {@code [X] buy groceries}.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns a pipe-delimited string representation of this task
     * suitable for saving to a file.
     *
     * @return A string in the format {@code isDone | description},
     *         where isDone is "1" if done and "0" otherwise.
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}