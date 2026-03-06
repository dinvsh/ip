package steve;

/**
 * Represents application-specific exceptions thrown by the Steve chatbot.
 * Used to signal user input errors, missing data, and other recoverable
 * error conditions during command processing. All error messages are
 * defined as constants in {@link Messages}.
 */
public class SteveException extends Exception {

    /**
     * Constructs a new SteveException with the given error message.
     *
     * @param message A descriptive message explaining the cause of the exception.
     */
    public SteveException(String message) {
        super(message);
    }
}