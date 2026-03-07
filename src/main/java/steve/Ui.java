package steve;

import java.util.Scanner;

/**
 * Handles all user-facing input and output for the Steve chatbot.
 * Reads commands from standard input and prints formatted responses
 * to standard output. Uses horizontal lines to visually separate messages.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance and initialises the input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input, trimmed of leading and trailing whitespace.
     *
     * @return The trimmed input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints the welcome message and initial prompt to the user.
     */
    public void showWelcome() {
        printBordered(Messages.WELCOME, Messages.PROMPT);
    }

    /**
     * Prints the goodbye message and closes the input scanner.
     */
    public void showGoodbye() {
        printBordered(Messages.GOODBYE);
        scanner.close();
    }

    /**
     * Prints a formatted error message with the system error prefix.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        printBordered(Messages.ERR_SYSTEM_PREFIX + message);
    }

    /**
     * Prints a single horizontal line separator to standard output.
     */
    public void showLine() {
        System.out.println(Messages.HORIZONTAL_LINE);
    }

    /**
     * Prints one or more messages surrounded by horizontal line borders.
     * Each message is indented by one space.
     *
     * @param messages One or more strings to print between the border lines.
     */
    public void printBordered(String... messages) {
        System.out.println(Messages.HORIZONTAL_LINE);
        for (String msg : messages) {
            System.out.println(" " + msg);
        }
        System.out.println(Messages.HORIZONTAL_LINE);
    }
}