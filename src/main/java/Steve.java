import java.util.Scanner;

public class Steve {
    public static void main(String[] args) {
        String name = "steve";
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        // Initialize the array
        Task[] tasks = new Task[100];
        int taskCount = 0;

        // Greeting
        System.out.println(line);
        System.out.println(" waddup im " + name);
        System.out.println(" whatchu want from me?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i].getDesc());
                }
                System.out.println(line);
            } else {
                // Store the task
                tasks[taskCount] = new Task(input);
                taskCount++;

                // Confirm addition
                System.out.println(line);
                System.out.println(" added: " + input);
                System.out.println(line);
            }
        }

        System.out.println(line);
        System.out.println(" see you next time cuh");
        System.out.println(line);

        scanner.close();
    }
}