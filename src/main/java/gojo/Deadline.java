package gojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * The deadline can be specified as a formatted date-time string (yyyy-MM-dd HHmm)
 * or as a plain text string if the format does not match.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    protected String byString;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructs a Deadline task with the given description and deadline string.
     * If the deadline string matches the expected format (yyyy-MM-dd HHmm),
     * it is parsed into a LocalDateTime. Otherwise, it is stored as a plain string.
     *
     * @param description The description of the deadline task.
     * @param by The deadline as a string, either in yyyy-MM-dd HHmm format or plain text.
     */
    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
            this.byString = null;
        } catch (DateTimeParseException e) {
            this.by = null;
            this.byString = by;        }
    }

    /**
     * Returns a string representation of the Deadline task.
     * If the deadline was successfully parsed, it is displayed in MMM dd yyyy, h:mma format.
     * Otherwise, the plain text deadline is displayed.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        String byDisplay = (by != null) ? by.format(OUTPUT_FORMAT) : byString;
        return "[D]" + super.toString() + " (by: " + byDisplay + ")";    }
}