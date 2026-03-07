package steve;

/**
 * Main class for the Steve chatbot application.
 * Steve is a task management chatbot that supports three types of tasks:
 * todos, deadlines, and events. It persists tasks to disk and provides
 * a command-line interface for managing them.
 */
public class Steve {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Steve chatbot instance.
     * Initialises the UI, storage, and task list. If loading from disk fails,
     * starts with an empty task list and reports the error.
     *
     * @param filePath The path to the file used for persisting tasks.
     */
    public Steve(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (SteveException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main command loop of the chatbot.
     * Continuously reads user input, parses commands, and dispatches to
     * the appropriate handler until the user enters "bye".
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            if (input.isEmpty()) continue;

            try {
                String command = input.split(" ")[0];
                switch (command) {
                case "bye":
                    isExit = true;
                    break;
                case "list":
                    printList();
                    break;
                case "mark":
                    int mIdx = Parser.parseIndex(input, "mark", tasks.getSize());
                    if (tasks.getTask(mIdx).isDone()) throw new SteveException(Messages.ERR_ALREADY_MARKED);
                    tasks.getTask(mIdx).mark();
                    ui.printBordered(Messages.MARKED, "  " + tasks.getTask(mIdx));
                    storage.save(tasks.getTasks());
                    break;
                case "unmark":
                    int uIdx = Parser.parseIndex(input, "unmark", tasks.getSize());
                    if (!tasks.getTask(uIdx).isDone()) throw new SteveException(Messages.ERR_NOT_MARKED);
                    tasks.getTask(uIdx).unmark();
                    ui.printBordered(Messages.UNMARKED, "  " + tasks.getTask(uIdx));
                    storage.save(tasks.getTasks());
                    break;
                case "delete":
                    int dIdx = Parser.parseIndex(input, "delete", tasks.getSize());
                    Task removed = tasks.removeTask(dIdx);
                    ui.printBordered(Messages.TASK_REMOVED, "  " + removed, String.format(Messages.TASK_COUNT, tasks.getSize()));
                    storage.save(tasks.getTasks());
                    break;
                case "todo":
                    String tDesc = Parser.parseTodo(input);
                    addTask(new Todo(tDesc));
                    break;
                case "deadline":
                    String[] dParts = Parser.parseDeadline(input);
                    addTask(new Deadline(dParts[0], dParts[1]));
                    break;
                case "event":
                    String[] eParts = Parser.parseEvent(input);
                    addTask(new Event(eParts[0], eParts[1], eParts[2]));
                    break;
                case "find":
                    String keyword = Parser.parseFind(input);
                    printFoundTasks(keyword);
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

    /**
     * Adds a task to the task list, prints a confirmation, and saves to disk.
     *
     * @param t The task to add.
     */
    private void addTask(Task t) {
        tasks.addTask(t);
        ui.printBordered(Messages.TASK_ADDED, "  " + t, String.format(Messages.TASK_COUNT, tasks.getSize()));
        try {
            storage.save(tasks.getTasks());
        } catch (SteveException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Prints the full task list to the console, numbered from 1.
     */
    private void printList() {
        ui.showLine();
        System.out.println(Messages.LIST_HEADER);
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
        }
        ui.showLine();
    }

    /**
     * Searches for tasks whose descriptions contain the given keyword
     * and prints the matching results, numbered from 1.
     *
     * @param keyword The keyword to search for within task descriptions.
     */
    private void printFoundTasks(String keyword) {
        ui.showLine();
        System.out.println(Messages.FIND_HEADER);
        int count = 1;
        for (int i = 0; i < tasks.getSize(); i++) {
            Task t = tasks.getTask(i);
            if (t.getDescription().contains(keyword)) {
                System.out.println(" " + count + "." + t);
                count++;
            }
        }
        ui.showLine();
    }

    /**
     * Entry point of the Steve chatbot application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Steve("data/steve.txt").run();
    }
}