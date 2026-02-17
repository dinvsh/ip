package steve;

import java.util.ArrayList;

public class Steve {
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";

    private static ArrayList<Task> tasks = new ArrayList<>();

    private static Ui ui;

    public static void main(String[] args) {
        ui = new Ui();
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            if (input.isEmpty()) continue;

            try {
                String command = input.split(" ")[0];
                switch (command) {
                case CMD_BYE:
                    isExit = true;
                    break;
                case CMD_LIST:
                    printList();
                    break;
                case CMD_MARK:
                    int mIdx = Parser.parseIndex(input, CMD_MARK, tasks.size());
                    if (tasks.get(mIdx).isDone()) throw new SteveException(Messages.ERR_ALREADY_MARKED);
                    tasks.get(mIdx).mark();
                    ui.printBordered(Messages.MARKED, "  " + tasks.get(mIdx));
                    break;
                case CMD_UNMARK:
                    int uIdx = Parser.parseIndex(input, CMD_UNMARK, tasks.size());
                    if (!tasks.get(uIdx).isDone()) throw new SteveException(Messages.ERR_NOT_MARKED);
                    tasks.get(uIdx).unmark();
                    ui.printBordered(Messages.UNMARKED, "  " + tasks.get(uIdx));
                    break;
                case CMD_DELETE:
                    int dIdx = Parser.parseIndex(input, CMD_DELETE, tasks.size());
                    deleteTask(dIdx);
                    break;
                case CMD_TODO:
                    String tDesc = Parser.parseTodo(input);
                    addTask(new Todo(tDesc));
                    break;
                case CMD_DEADLINE:
                    String[] dParts = Parser.parseDeadline(input);
                    addTask(new Deadline(dParts[0], dParts[1]));
                    break;
                case CMD_EVENT:
                    String[] eParts = Parser.parseEvent(input);
                    addTask(new Event(eParts[0], eParts[1], eParts[2]));
                    break;
                default:
                    throw new SteveException(Messages.ERR_UNKNOWN_CMD);
                }
            } catch (SteveException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showGoodbye();
    }

    private static void addTask(Task t) {
        tasks.add(t);;
        ui.printBordered(Messages.TASK_ADDED, "  " + t, String.format(Messages.TASK_COUNT, tasks.size()));
    }

    private static void deleteTask(int index) {
        Task removedTask = tasks.remove(index);
        ui.printBordered(Messages.TASK_REMOVED, "  " + removedTask, String.format(Messages.TASK_COUNT, tasks.size()));
    }

    private static void printList() {
        ui.showLine();
        System.out.println(Messages.LIST_HEADER);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        ui.showLine();
    }
}