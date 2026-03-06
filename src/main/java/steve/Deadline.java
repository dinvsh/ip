package steve;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDate byDate;
    protected String byString;

    public Deadline(String description, String by) throws SteveException {
        super(description);
        try {
            this.byDate = LocalDate.parse(by);
            this.byString = by; // keep the original string for saving to file
        } catch (DateTimeParseException e) {
            throw new SteveException(Messages.ERR_BAD_DATE_FORMAT);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + byString;
    }
}