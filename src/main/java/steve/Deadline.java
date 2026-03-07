package steve;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * Deadlines are displayed with the {@code [D]} prefix and show the due date
 * in {@code MMM dd yyyy} format (e.g., {@code Jan 15 2025}).
 * The date must be supplied in {@code yyyy-MM-dd} format.
 */
public class Deadline extends Task {
    protected LocalDate byDate;
    protected String byString;

    /**
     * Constructs a new Deadline task.
     *
     * @param description A non-empty string describing the task.
     * @param by          The deadline date in {@code yyyy-MM-dd} format (e.g., {@code 2025-12-31}).
     * @throws SteveException If {@code by} cannot be parsed as a valid date.
     */
    public Deadline(String description, String by) throws SteveException {
        super(description);
        try {
            this.byDate = LocalDate.parse(by);
            this.byString = by;
        } catch (DateTimeParseException e) {
            throw new SteveException(Messages.ERR_BAD_DATE_FORMAT);
        }
    }

    /**
     * Returns a human-readable string representation of this Deadline,
     * prefixed with {@code [D]} and including the formatted due date.
     *
     * @return A formatted string such as {@code [D][ ] submit report (by: Dec 31 2025)}.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    /**
     * Returns a pipe-delimited string representation of this Deadline
     * suitable for saving to a file.
     *
     * @return A string in the format {@code D | isDone | description | yyyy-MM-dd}.
     */
    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + byString;
    }
}