public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDesc() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    // New methods for Level-3
    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }
}