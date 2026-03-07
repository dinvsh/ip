package steve;

/**
 * Deals with making sense of user commands.
 * Parses raw input strings into actionable data or indices,
 * throwing descriptive {@link SteveException}s for malformed input.
 * All methods are static.
 */
public class Parser {

    /**
     * Parses a {@code find} command and returns the search keyword.
     *
     * @param input The full raw user input (e.g., {@code find book}).
     * @return The trimmed keyword string to search for.
     * @throws SteveException If no keyword is provided after {@code find}.
     */
    public static String parseFind(String input) throws SteveException {
        if (input.length() <= 5 || input.substring(4).trim().isEmpty()) {
            throw new SteveException(Messages.ERR_EMPTY_FIND);
        }
        return input.substring(5).trim();
    }

    /**
     * Parses the user input to extract a task index.
     *
     * @param input       The raw user input string (e.g., {@code mark 2}).
     * @param commandType The type of command (e.g., {@code "mark"}, {@code "delete"})
     *                    used in error messages.
     * @param taskCount   The current number of tasks in the list, used to validate bounds.
     * @return The zero-based index of the task.
     * @throws SteveException If the index is missing, not a number, or out of bounds.
     */
    public static int parseIndex(String input, String commandType, int taskCount) throws SteveException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new SteveException(String.format(Messages.ERR_MISSING_INDEX, commandType));
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new SteveException(Messages.ERR_TASK_NOT_FOUND);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SteveException(Messages.ERR_INVALID_NUMBER);
        }
    }

    /**
     * Parses a {@code todo} command and returns the task description.
     *
     * @param input The full raw user input (e.g., {@code todo buy groceries}).
     * @return The trimmed task description.
     * @throws SteveException If no description is provided.
     */
    public static String parseTodo(String input) throws SteveException {
        if (input.length() <= 4) {
            throw new SteveException(Messages.ERR_EMPTY_DESC);
        }
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            throw new SteveException(Messages.ERR_EMPTY_DESC);
        }
        return desc;
    }

    /**
     * Parses a {@code deadline} command and returns its components.
     *
     * @param input The full raw user input
     *              (e.g., {@code deadline submit report /by 2025-12-31}).
     * @return A two-element array: {@code [description, byDate]}.
     * @throws SteveException If the description or {@code /by} date is missing or malformed.
     */
    public static String[] parseDeadline(String input) throws SteveException {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) throw new SteveException(Messages.ERR_MISSING_BY);

        String desc = input.substring(8, byIndex).trim();
        if (desc.isEmpty()) throw new SteveException(Messages.ERR_EMPTY_DESC);

        if (byIndex + 3 >= input.length()) throw new SteveException(Messages.ERR_MISSING_DATE);

        String by = input.substring(byIndex + 3).trim();
        if (by.isEmpty()) throw new SteveException(Messages.ERR_MISSING_DATE);

        return new String[]{desc, by};
    }

    /**
     * Parses an {@code event} command and returns its components.
     *
     * @param input The full raw user input
     *              (e.g., {@code event orientation /from 2025-08-01 /to 2025-08-03}).
     * @return A three-element array: {@code [description, fromDate, toDate]}.
     * @throws SteveException If any component is missing or {@code /from} appears after {@code /to}.
     */
    public static String[] parseEvent(String input) throws SteveException {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) throw new SteveException(Messages.ERR_MISSING_FROM_TO);
        if (fromIndex > toIndex) throw new SteveException(Messages.ERR_START_AFTER_END);

        String desc = input.substring(5, fromIndex).trim();
        if (desc.isEmpty()) throw new SteveException(Messages.ERR_EMPTY_DESC);

        if (fromIndex + 5 >= input.length()) throw new SteveException(Messages.ERR_MISSING_START_DATE);

        String from = input.substring(fromIndex + 5, toIndex).trim();
        if (from.isEmpty()) throw new SteveException(Messages.ERR_MISSING_START_DATE);

        if (toIndex + 3 >= input.length()) throw new SteveException(Messages.ERR_MISSING_END_DATE);

        String to = input.substring(toIndex + 3).trim();
        if (to.isEmpty()) throw new SteveException(Messages.ERR_MISSING_END_DATE);

        return new String[]{desc, from, to};
    }
}