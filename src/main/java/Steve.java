import java.util.Scanner;

public class Steve {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    private static final int TODO_CMD_LENGTH = 5;
    private static final int DEADLINE_CMD_LENGTH = 9;
    private static final int EVENT_CMD_LENGTH = 6;
    private static final int BY_PREFIX_LENGTH = 4;
    private static final int FROM_PREFIX_LENGTH = 6;
    private static final int TO_PREFIX_LENGTH = 4;

    private static final String MSG_WELCOME = "waddup im steve";
    private static final String MSG_PROMPT = "whatchu want from me?";
    private static final String MSG_GOODBYE = "see you next time dawg";
    private static final String MSG_LIST_HEADER = " here's your stuff cuh:";
    private static final String MSG_TASK_ADDED = "gotchu i added this task:";
    private static final String MSG_TASK_COUNT = "now you got %d tasks in the list"; // <--- FIXED
    private static final String MSG_MARKED = "aight i marked it done:";
    private static final String MSG_UNMARKED = "ok i unmarked it:";

    private static final String ERROR_SYSTEM_PREFIX = "uh oh: ";
    private static final String ERROR_INVALID_NUMBER = "that aint a number bro";
    private static final String ERROR_TASK_NOT_FOUND = "that task number does not exist cuh";
    private static final String ERROR_MISSING_INDEX = "you gotta tell me which one to %s cuh";
    private static final String ERROR_EMPTY_DESCRIPTION = "bro u gotta say what u wanna do";
    private static final String ERROR_MISSING_BY = "bro where's the /by?";
    private static final String ERROR_MISSING_FROM_TO = "bro where's the /from and /to?";
    private static final String ERROR_ALREADY_MARKED = "bro its already marked done, u good?";
    private static final String ERROR_NOT_MARKED = "bro it aint even marked yet, how u gonna unmark it?";
    private static final String ERROR_UNKNOWN_CMD = "idk what that means cuh";

    private static final String ERROR_MISSING_DATE = "bro u gotta tell me when";
    private static final String ERROR_START_AFTER_END = "bro u gotta put /from before /to";
    private static final String ERROR_MISSING_START_DATE = "bro u gotta tell me when it starts";
    private static final String ERROR_MISSING_END_DATE = "bro u gotta tell me when it ends";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printBordered(MSG_WELCOME, MSG_PROMPT);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            String command = input.split(" ")[0];
            try {
                if (input.equals("bye")) {
                    break;
                } else if (input.equals("list")) {
                    printList();
                } else if (command.equals("mark")) {
                    handleMark(input);
                } else if (command.equals("unmark")) {
                    handleUnmark(input);
                } else if (command.equals("todo")) {
                    addTodo(input);
                } else if (command.equals("deadline")) {
                    addDeadline(input);
                } else if (command.equals("event")) {
                    addEvent(input);
                } else {
                    printBordered(ERROR_UNKNOWN_CMD);
                }
            } catch (Exception e) {
                printBordered(ERROR_SYSTEM_PREFIX + e.getMessage());
            }
        }

        printBordered(MSG_GOODBYE);
        scanner.close();
    }

    private static void handleMark(String input) {
        try {
            int index = parseTaskIndex(input, "mark");
            if (index == -1) return;

            Task task = tasks[index];
            if (task.isDone()) {
                printBordered(ERROR_ALREADY_MARKED);
            } else {
                task.mark();
                printBordered(MSG_MARKED, "  " + task.toString());
            }
        } catch (NumberFormatException e) {
            printBordered(ERROR_INVALID_NUMBER);
        }
    }

    private static void handleUnmark(String input) {
        try {
            int index = parseTaskIndex(input, "unmark");
            if (index == -1) return;

            Task task = tasks[index];
            if (!task.isDone()) {
                printBordered(ERROR_NOT_MARKED);
            } else {
                task.unmark();
                printBordered(MSG_UNMARKED, "  " + task.toString());
            }
        } catch (NumberFormatException e) {
            printBordered(ERROR_INVALID_NUMBER);
        }
    }

    private static int parseTaskIndex(String input, String action) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            printBordered(String.format(ERROR_MISSING_INDEX, action));
            return -1;
        }

        int index = Integer.parseInt(parts[1]) - 1;
        if (!isValidIndex(index)) {
            printBordered(ERROR_TASK_NOT_FOUND);
            return -1;
        }
        return index;
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index < taskCount;
    }

    private static void addTodo(String input) {
        if (input.length() <= TODO_CMD_LENGTH) {
            printBordered(ERROR_EMPTY_DESCRIPTION);
            return;
        }
        String desc = input.substring(TODO_CMD_LENGTH).trim();
        addTask(new Todo(desc));
    }

    private static void addDeadline(String input) {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            printBordered(ERROR_MISSING_BY);
            return;
        }

        String desc = input.substring(DEADLINE_CMD_LENGTH, byIndex).trim();
        if (desc.isEmpty()) {
            printBordered(ERROR_EMPTY_DESCRIPTION);
            return;
        }

        if (byIndex + BY_PREFIX_LENGTH >= input.length()) {
            printBordered(ERROR_MISSING_DATE);
            return;
        }

        String by = input.substring(byIndex + BY_PREFIX_LENGTH).trim();
        if (by.isEmpty()) {
            printBordered(ERROR_MISSING_DATE);
            return;
        }

        addTask(new Deadline(desc, by));
    }

    private static void addEvent(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            printBordered(ERROR_MISSING_FROM_TO);
            return;
        }

        if (fromIndex > toIndex) {
            printBordered(ERROR_START_AFTER_END);
            return;
        }

        String desc = input.substring(EVENT_CMD_LENGTH, fromIndex).trim();
        if (desc.isEmpty()) {
            printBordered(ERROR_EMPTY_DESCRIPTION);
            return;
        }

        if (fromIndex + FROM_PREFIX_LENGTH >= input.length()) {
            printBordered(ERROR_MISSING_START_DATE);
            return;
        }
        String from = input.substring(fromIndex + FROM_PREFIX_LENGTH, toIndex).trim();
        if (from.isEmpty()) {
            printBordered(ERROR_MISSING_START_DATE);
            return;
        }

        if (toIndex + TO_PREFIX_LENGTH >= input.length()) {
            printBordered(ERROR_MISSING_END_DATE);
            return;
        }
        String to = input.substring(toIndex + TO_PREFIX_LENGTH).trim();
        if (to.isEmpty()) {
            printBordered(ERROR_MISSING_END_DATE);
            return;
        }

        addTask(new Event(desc, from, to));
    }

    private static void addTask(Task t) {
        tasks[taskCount] = t;
        taskCount++;

        printBordered(
                MSG_TASK_ADDED,
                "  " + t.toString(),
                String.format(MSG_TASK_COUNT, taskCount) // <--- Clean usage of %d
        );
    }

    private static void printList() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(MSG_LIST_HEADER);
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