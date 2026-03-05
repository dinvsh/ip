package steve;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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