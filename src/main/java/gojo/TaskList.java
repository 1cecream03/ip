package gojo;

import java.util.ArrayList;

/**
 * Represents the list of tasks managed by the Gojo chatbot.
 * Provides operations to add, delete, retrieve, and search tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The existing list of tasks to initialise with.
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
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task at the given index from the list.
     *
     * @param index The zero-based index of the task to delete.
     * @return The task that was removed.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task at the given index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a formatted string of all tasks in the list.
     *
     * @return A numbered list of all tasks as a string.
     */
    public String listTasks() {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            result += (i + 1) + ". " + tasks.get(i) + "\n";
        }
        return result;
    }

    /**
     * Searches for tasks whose description contains the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A formatted string of matching tasks, or a message if none are found.
     */
    public String findTasks(String keyword) {
        String result = "Here are the matching tasks in your list:\n";
        int count = 1;
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                result += count + ". " + task + "\n";
                count++;
            }
        }
        return count == 1 ? "No tasks found with the keyword: " + keyword : result;
    }
}