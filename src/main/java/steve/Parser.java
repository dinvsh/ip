public class Parser {

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

    public static String parseTodo(String input) throws SteveException {
        if (input.length() <= 5) {
            throw new SteveException(Messages.ERR_EMPTY_DESC);
        }
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            throw new SteveException(Messages.ERR_EMPTY_DESC);
        }
        return desc;
    }

    public static String[] parseDeadline(String input) throws SteveException {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) throw new SteveException(Messages.ERR_MISSING_BY);

        String desc = input.substring(9, byIndex).trim();
        if (desc.isEmpty()) throw new SteveException(Messages.ERR_EMPTY_DESC);

        // Safe check: ensure there is text after "/by"
        if (byIndex + 4 >= input.length()) throw new SteveException(Messages.ERR_MISSING_DATE);

        String by = input.substring(byIndex + 4).trim();
        if (by.isEmpty()) throw new SteveException(Messages.ERR_MISSING_DATE);

        return new String[]{desc, by};
    }

    public static String[] parseEvent(String input) throws SteveException {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) throw new SteveException(Messages.ERR_MISSING_FROM_TO);
        if (fromIndex > toIndex) throw new SteveException(Messages.ERR_START_AFTER_END);

        String desc = input.substring(6, fromIndex).trim();
        if (desc.isEmpty()) throw new SteveException(Messages.ERR_EMPTY_DESC);

        // Safe check: ensure there is text after "/from"
        if (fromIndex + 6 >= input.length()) throw new SteveException(Messages.ERR_MISSING_START_DATE);

        String from = input.substring(fromIndex + 6, toIndex).trim();
        if (from.isEmpty()) throw new SteveException(Messages.ERR_MISSING_START_DATE);

        // Safe check: ensure there is text after "/to"
        if (toIndex + 4 >= input.length()) throw new SteveException(Messages.ERR_MISSING_END_DATE);

        String to = input.substring(toIndex + 4).trim();
        if (to.isEmpty()) throw new SteveException(Messages.ERR_MISSING_END_DATE);

        return new String[]{desc, from, to};
    }
}