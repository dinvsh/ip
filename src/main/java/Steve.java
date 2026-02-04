import java.util.Scanner;

public class Steve {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";

    private static final int TODO_CMD_LENGTH = 5;
    private static final int DEADLINE_CMD_LENGTH = 9;
    private static final int EVENT_CMD_LENGTH = 6;
    private static final int BY_PREFIX_LENGTH = 4;
    private static final int FROM_PREFIX_LENGTH = 6;
    private static final int TO_PREFIX_LENGTH = 4;

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printBordered(Messages.WELCOME, Messages.PROMPT);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                if (processCommand(input)) {
                    break;
                }
            } catch (Exception e) {
                printBordered(Messages.ERR_SYSTEM_PREFIX + e.getMessage());
            }
        }

        printBordered(Messages.GOODBYE);
        scanner.close();
    }

    /**
     * Processes the user input and executes the corresponding command.
     * Returns true if the program should exit (i.e., "bye" command).
     */
    private static boolean processCommand(String input) {
        String command = input.split(" ")[0];

        switch (command) {
        case CMD_BYE:
            return true;
        case CMD_LIST:
            printList();
            break;
        case CMD_MARK:
            handleMark(input);
            break;
        case CMD_UNMARK:
            handleUnmark(input);
            break;
        case CMD_TODO:
            addTodo(input);
            break;
        case CMD_DEADLINE:
            addDeadline(input);
            break;
        case CMD_EVENT:
            addEvent(input);
            break;
        default:
            printBordered(Messages.ERR_UNKNOWN_CMD);
        }
        return false;
    }

    private static void handleMark(String input) {
        try {
            int index = parseTaskIndex(input, "mark");
            if (index == -1) return;

            Task task = tasks[index];
            if (task.isDone()) {
                printBordered(Messages.ERR_ALREADY_MARKED);
            } else {
                task.mark();
                printBordered(Messages.MARKED, "  " + task.toString());
            }
        } catch (NumberFormatException e) {
            printBordered(Messages.ERR_INVALID_NUMBER);
        }
    }

    private static void handleUnmark(String input) {
        try {
            int index = parseTaskIndex(input, "unmark");
            if (index == -1) return;

            Task task = tasks[index];
            if (!task.isDone()) {
                printBordered(Messages.ERR_NOT_MARKED);
            } else {
                task.unmark();
                printBordered(Messages.UNMARKED, "  " + task.toString());
            }
        } catch (NumberFormatException e) {
            printBordered(Messages.ERR_INVALID_NUMBER);
        }
    }

    private static int parseTaskIndex(String input, String action) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            printBordered(String.format(Messages.ERR_MISSING_INDEX, action));
            return -1;
        }

        int index = Integer.parseInt(parts[1]) - 1;

        if (!isValidIndex(index)) {
            printBordered(Messages.ERR_TASK_NOT_FOUND);
            return -1;
        }

        return index;
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index < taskCount;
    }

    private static void addTodo(String input) {
        if (input.length() <= TODO_CMD_LENGTH) {
            printBordered(Messages.ERR_EMPTY_DESC);
            return;
        }
        String desc = input.substring(TODO_CMD_LENGTH).trim();
        addTask(new Todo(desc));
    }

    private static void addDeadline(String input) {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            printBordered(Messages.ERR_MISSING_BY);
            return;
        }

        String desc = input.substring(DEADLINE_CMD_LENGTH, byIndex).trim();
        if (desc.isEmpty()) {
            printBordered(Messages.ERR_EMPTY_DESC);
            return;
        }

        if (byIndex + BY_PREFIX_LENGTH >= input.length()) {
            printBordered(Messages.ERR_MISSING_DATE);
            return;
        }

        String by = input.substring(byIndex + BY_PREFIX_LENGTH).trim();
        if (by.isEmpty()) {
            printBordered(Messages.ERR_MISSING_DATE);
            return;
        }

        addTask(new Deadline(desc, by));
    }

    private static void addEvent(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            printBordered(Messages.ERR_MISSING_FROM_TO);
            return;
        }

        if (fromIndex > toIndex) {
            printBordered(Messages.ERR_START_AFTER_END);
            return;
        }

        String desc = input.substring(EVENT_CMD_LENGTH, fromIndex).trim();
        if (desc.isEmpty()) {
            printBordered(Messages.ERR_EMPTY_DESC);
            return;
        }

        if (fromIndex + FROM_PREFIX_LENGTH >= input.length()) {
            printBordered(Messages.ERR_MISSING_START_DATE);
            return;
        }
        String from = input.substring(fromIndex + FROM_PREFIX_LENGTH, toIndex).trim();
        if (from.isEmpty()) {
            printBordered(Messages.ERR_MISSING_START_DATE);
            return;
        }

        if (toIndex + TO_PREFIX_LENGTH >= input.length()) {
            printBordered(Messages.ERR_MISSING_END_DATE);
            return;
        }
        String to = input.substring(toIndex + TO_PREFIX_LENGTH).trim();
        if (to.isEmpty()) {
            printBordered(Messages.ERR_MISSING_END_DATE);
            return;
        }

        addTask(new Event(desc, from, to));
    }

    private static void addTask(Task t) {
        tasks[taskCount] = t;
        taskCount++;

        printBordered(
                Messages.TASK_ADDED,
                "  " + t.toString(),
                String.format(Messages.TASK_COUNT, taskCount)
        );
    }

    private static void printList() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(Messages.LIST_HEADER);
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i].toString());
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printBordered(String... messages) {
        System.out.println(HORIZONTAL_LINE);
        for (String msg : messages) {
            System.out.println(" " + msg);
        }
        System.out.println(HORIZONTAL_LINE);
    }
}