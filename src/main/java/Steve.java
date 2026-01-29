import java.util.Scanner;

public class Steve {
    public static void main(String[] args) {
        String name = "steve";
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        // Greeting
        System.out.println(line);
        System.out.println(" waddup im " + name);
        System.out.println(" whatchu want from me?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine();

            // Basic command handling
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                System.out.println(" here's your stuff cuh:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDesc());
                }
                System.out.println(line);

                // MARK COMMAND
            } else if (input.startsWith("mark")) {
                String[] parts = input.split(" ");

                // EDGE CASE: Did they type a number?
                if (parts.length < 2) {
                    System.out.println(line);
                    System.out.println(" you gotta tell me which one to mark cuh");
                    System.out.println(line);
                    continue; // Skip the rest of the loop
                }

                try {
                    int index = Integer.parseInt(parts[1]) - 1;

                    // Check if number exists
                    if (index < 0 || index >= taskCount) {
                        System.out.println(line);
                        System.out.println(" that task number does not exist cuh");
                        System.out.println(line);
                        // Check if already marked
                    } else if (tasks[index].isDone()) {
                        System.out.println(line);
                        System.out.println(" bro its already marked done, u good?");
                        System.out.println(line);
                        // Mark it
                    } else {
                        tasks[index].mark();
                        System.out.println(line);
                        System.out.println(" aight i marked it done:");
                        System.out.println("   [X] " + tasks[index].getDesc());
                        System.out.println(line);
                    }
                } catch (NumberFormatException e) {
                    // EDGE CASE: Did they type a valid integer?
                    System.out.println(line);
                    System.out.println(" that aint a number bro");
                    System.out.println(line);
                }

                // UNMARK COMMAND
            } else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");

                // EDGE CASE: Did they type a number?
                if (parts.length < 2) {
                    System.out.println(line);
                    System.out.println(" you gotta tell me which one to unmark cuh");
                    System.out.println(line);
                    continue;
                }

                try {
                    int index = Integer.parseInt(parts[1]) - 1;

                    // Check if number exists
                    if (index < 0 || index >= taskCount) {
                        System.out.println(line);
                        System.out.println(" that task number does not exist cuh");
                        System.out.println(line);
                        // Check if already unmarked
                    } else if (!tasks[index].isDone()) {
                        System.out.println(line);
                        System.out.println(" bro it aint even marked yet, how u gonna unmark it?");
                        System.out.println(line);
                        // Unmark it
                    } else {
                        tasks[index].unmark();
                        System.out.println(line);
                        System.out.println(" ok i unmarked it:");
                        System.out.println("   [ ] " + tasks[index].getDesc());
                        System.out.println(line);
                    }
                } catch (NumberFormatException e) {
                    // EDGE CASE: Did they type a valid integer?
                    System.out.println(line);
                    System.out.println(" that aint a number bro");
                    System.out.println(line);
                }

                // ADD TASK
            } else {
                if (input.trim().isEmpty()) {
                    System.out.println(line);
                    System.out.println(" you gotta type something cuh, cant add air");
                    System.out.println(line);
                } else {
                    tasks[taskCount] = new Task(input);
                    taskCount++;

                    System.out.println(line);
                    System.out.println(" added: " + input);
                    System.out.println(line);
                }
            }
        }

        System.out.println(line);
        System.out.println(" see you next time cuh");
        System.out.println(line);

        scanner.close();
    }
}