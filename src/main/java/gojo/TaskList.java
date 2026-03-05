package gojo;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public String listTasks() {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            result += (i + 1) + ". " + tasks.get(i) + "\n";
        }
        return result;
    }

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