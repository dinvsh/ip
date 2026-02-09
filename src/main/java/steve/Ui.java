import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showWelcome() {
        printBordered(Messages.WELCOME, Messages.PROMPT);
    }

    public void showGoodbye() {
        printBordered(Messages.GOODBYE);
        scanner.close();
    }

    public void showError(String message) {
        printBordered(Messages.ERR_SYSTEM_PREFIX + message);
    }

    public void showLine() {
        System.out.println(Messages.HORIZONTAL_LINE);
    }

    public void printBordered(String... messages) {
        System.out.println(Messages.HORIZONTAL_LINE);
        for (String msg : messages) {
            System.out.println(" " + msg);
        }
        System.out.println(Messages.HORIZONTAL_LINE);
    }
}