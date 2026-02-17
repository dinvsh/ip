package steve;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Steve {
    private static final String FILE_PATH = "data/steve.txt";
    private static final int MAX_TASKS = 100;

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    private static Ui ui;

    public static void main(String[] args) {
        ui = new Ui();
        ui.showWelcome();
        loadTasks();

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
                    int mIdx = Parser.parseIndex(input, CMD_MARK, taskCount);
                    if (tasks[mIdx].isDone()) throw new SteveException(Messages.ERR_ALREADY_MARKED);
                    tasks[mIdx].mark();
                    ui.printBordered(Messages.MARKED, "  " + tasks[mIdx]);
                    saveTasks();
                    break;
                case CMD_UNMARK:
                    int uIdx = Parser.parseIndex(input, CMD_UNMARK, taskCount);
                    if (!tasks[uIdx].isDone()) throw new SteveException(Messages.ERR_NOT_MARKED);
                    tasks[uIdx].unmark();
                    ui.printBordered(Messages.UNMARKED, "  " + tasks[uIdx]);
                    saveTasks();
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
        tasks[taskCount++] = t;
        ui.printBordered(Messages.TASK_ADDED, "  " + t, String.format(Messages.TASK_COUNT, taskCount));
        saveTasks();
    }

    private static void printList() {
        ui.showLine();
        System.out.println(Messages.LIST_HEADER);
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
        ui.showLine();
    }

    private static void saveTasks() {
        try {
            // Create the "data" folder if it doesn't exist
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Write all tasks to the file
            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                fw.write(tasks[i].toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private static void loadTasks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String desc = parts[2];

                    Task t = null;
                    if (type.equals("T")) {
                        t = new Todo(desc);
                    } else if (type.equals("D")) {
                        t = new Deadline(desc, parts[3]);
                    } else if (type.equals("E")) {
                        t = new Event(desc, parts[3], parts[4]);
                    }

                    if (t != null && taskCount < 100) {
                        if (isDone) t.mark();
                        tasks[taskCount++] = t;
                    }
                } catch (Exception e) {
                    System.out.println("Skipping corrupted data line: " + line);
                }
            }
            scanner.close();

        } catch (Exception e) {
            System.out.println("Error reading the hard drive.");
        }
    }
}