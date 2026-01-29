import java.util.Scanner;

public class Steve {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        String name = "steve";
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        printBordered("waddup im " + name, "whatchu want from me?");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                // We build the list into an array of strings first
                System.out.println(HORIZONTAL_LINE);
                System.out.println(" here's your stuff cuh:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
                System.out.println(HORIZONTAL_LINE);

            } else if (input.startsWith("mark")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    printBordered("you gotta tell me which one to mark cuh");
                    continue;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= taskCount) {
                        printBordered("that task number does not exist cuh");
                    } else if (tasks[index].isDone()) {
                        printBordered("bro its already marked done, u good?");
                    } else {
                        tasks[index].mark();
                        printBordered("aight i marked it done:", "  [X] " + tasks[index].getDescription());
                    }
                } catch (NumberFormatException e) {
                    printBordered("that aint a number bro");
                }

            } else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    printBordered("you gotta tell me which one to unmark cuh");
                    continue;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= taskCount) {
                        printBordered("that task number does not exist cuh");
                    } else if (!tasks[index].isDone()) {
                        printBordered("bro it aint even marked yet, how u gonna unmark it?");
                    } else {
                        tasks[index].unmark();
                        printBordered("ok i unmarked it:", "  [ ] " + tasks[index].getDescription());
                    }
                } catch (NumberFormatException e) {
                    printBordered("that aint a number bro");
                }

            } else {
                // Add Task
                if (input.trim().isEmpty()) {
                    printBordered("you gotta type something cuh, cant add air");
                } else {
                    tasks[taskCount] = new Task(input);
                    taskCount++;
                    printBordered("added: " + input);
                }
            }
        }

        printBordered("see you next time cuh");
        scanner.close();
    }

    // --- HELPER METHOD ---
    private static void printBordered(String... messages) {
        System.out.println(HORIZONTAL_LINE);
        for (String msg : messages) {
            System.out.println(" " + msg);
        }
        System.out.println(HORIZONTAL_LINE);
    }
}