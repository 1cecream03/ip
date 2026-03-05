package gojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    protected String byString;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
            this.byString = null;
        } catch (DateTimeParseException e) {
            this.by = null;
            this.byString = by;        }
    }

    @Override
    public String toString() {
        String byDisplay = (by != null) ? by.format(OUTPUT_FORMAT) : byString;
        return "[D]" + super.toString() + " (by: " + byDisplay + ")";    }
}