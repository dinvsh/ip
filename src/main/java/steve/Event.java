package steve;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that spans a date range.
 * Events are displayed with the {@code [E]} prefix and show both dates
 * in {@code MMM dd yyyy} format (e.g., {@code Jan 15 2025}).
 * Both dates must be supplied in {@code yyyy-MM-dd} format.
 */
public class Event extends Task {
    protected LocalDate fromDate;
    protected LocalDate toDate;
    protected String fromString;
    protected String toString;

    /**
     * Constructs a new Event task.
     *
     * @param description A non-empty string describing the event.
     * @param from        The start date in {@code yyyy-MM-dd} format.
     * @param to          The end date in {@code yyyy-MM-dd} format.
     * @throws SteveException If either date cannot be parsed as a valid date.
     */
    public Event(String description, String from, String to) throws SteveException {
        super(description);
        try {
            this.fromDate = LocalDate.parse(from);
            this.fromString = from;
            this.toDate = LocalDate.parse(to);
            this.toString = to;
        } catch (DateTimeParseException e) {
            throw new SteveException(Messages.ERR_BAD_DATE_FORMAT);
        }
    }

    /**
     * Returns a human-readable string representation of this Event,
     * prefixed with {@code [E]} and including the formatted date range.
     *
     * @return A formatted string such as
     *         {@code [E][ ] orientation (from: Aug 01 2025 to: Aug 03 2025)}.
     */
    @Override
    public String toString() {
        String formattedFrom = fromDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        String formattedTo = toDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    /**
     * Returns a pipe-delimited string representation of this Event
     * suitable for saving to a file.
     *
     * @return A string in the format {@code E | isDone | description | yyyy-MM-dd | yyyy-MM-dd}.
     */
    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + fromString + " | " + toString;
    }
}