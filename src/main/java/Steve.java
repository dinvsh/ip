import java.util.Scanner;

public class Steve {
    public static void main(String[] args) {
        String name = "steve";
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in); // Create a scanner to read input

        // Greeting
        System.out.println(line);
        System.out.println(" waddup im " + name);
        System.out.println(" whatchu want from me?");
        System.out.println(line);

        // Echo Loop
        while (true) {
            String input = scanner.nextLine(); // Read user input

            if (input.equals("bye")) { // Check if user wants to exit
                break;
            }

            // Echo the command
            System.out.println(line);
            System.out.println(" " + input);
            System.out.println(line);
        }

        // Exit Message
        System.out.println(line);
        System.out.println(" see you next time cuh");
        System.out.println(line);

        scanner.close();
    }
}