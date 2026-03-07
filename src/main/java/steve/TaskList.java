package steve;

import java.util.ArrayList;

/**
 * Manages an ordered list of {@link Task} objects.
 * Provides methods to add, remove, retrieve, and count tasks.
 * Acts as the in-memory data store for the chatbot session.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList pre-populated with an existing list of tasks.
     * Typically used when loading tasks from disk on startup.
     *
     * @param tasks An {@link ArrayList} of tasks to initialise the list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param t The task to add.
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The zero-based index of the task to remove.
     * @return The removed {@link Task}.
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index without removing it.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The {@link Task} at the given index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The total task count.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     * Used primarily when saving tasks to disk.
     *
     * @return The {@link ArrayList} of all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}