package steve;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to and from disk.
 * Tasks are stored in a plain-text file using a pipe-delimited format.
 * Each line represents one task. Corrupted lines are skipped with a warning.
 * The data directory is created automatically if it does not exist.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance pointing to the given file path.
     *
     * @param filePath The path to the file where tasks are persisted
     *                 (e.g., {@code "data/steve.txt"}).
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads and returns the list of tasks from disk.
     * If the file does not exist, returns an empty list. Lines that cannot
     * be parsed are skipped with a warning message printed to stdout.
     *
     * @return An {@link ArrayList} of tasks read from the file.
     * @throws SteveException If the file exists but cannot be read.
     */
    public ArrayList<Task> load() throws SteveException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return loadedTasks;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

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

                    if (t != null) {
                        if (isDone) t.mark();
                        loadedTasks.add(t);
                    }
                } catch (Exception e) {
                    System.out.println("skipping corrupted data line: " + line);
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new SteveException("error reading the hard drive.");
        }
        return loadedTasks;
    }

    /**
     * Saves the current list of tasks to the file on disk.
     * Creates the {@code data} directory if it does not already exist.
     *
     * @param tasks The list of tasks to save.
     * @throws SteveException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws SteveException {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new SteveException("Error saving to file: " + e.getMessage());
        }
    }
}