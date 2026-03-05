package gojo;

/**
 * Represents exceptions specific to the Gojo chatbot application.
 * Used to handle invalid commands and other application-specific errors.
 */
public class GojoException extends Exception {

    /**
     * Constructs a GojoException with the given error message.
     *
     * @param message The error message describing the exception.
     */
    public GojoException(String message) {
        super(message);
    }
}