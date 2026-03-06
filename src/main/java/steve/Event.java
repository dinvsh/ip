package steve;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDate fromDate;
    protected LocalDate toDate;
    protected String fromString;
    protected String toString;

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

    @Override
    public String toString() {
        String formattedFrom = fromDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        String formattedTo = toDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + fromString + " | " + toString;
    }
}