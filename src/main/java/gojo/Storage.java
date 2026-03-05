package gojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

/**
 * Handles loading and saving of tasks to a file for the Gojo chatbot.
 * Tasks are stored in a pipe-separated format in a text file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath The path to the file where tasks are saved and loaded from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given list of tasks to the file.
     * Each task is written as a pipe-separated string on a new line.
     *
     * @param tasks The list of tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks) {
                fw.write(taskToSaveString(task) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object into a pipe-separated string for saving to file.
     * Format: TYPE | STATUS | DESCRIPTION | EXTRA (if applicable)
     *
     * @param task The task to convert.
     * @return A pipe-separated string representation of the task.
     */
    private String taskToSaveString(Task task) {
        String type = "";
        String status = task.isDone ? "1" : "0";
        String description = task.description;
        String extra = "";

        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            Deadline d = (Deadline) task;
            extra = " | " + (d.by != null
                    ? d.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                    : d.byString);
        } else if (task instanceof Event) {
            type = "E";
            extra = " | " + ((Event) task).from + " | " + ((Event) task).to;
        }

        return type + " | " + status + " | " + description + extra;
    }

    /**
     * Loads tasks from the file and returns them as an ArrayList.
     * If the file does not exist, it creates a new empty file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws GojoException If the file cannot be created or loaded.
     */
    public ArrayList<Task> load() throws GojoException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Create "data" folder
                file.createNewFile();
            } catch (IOException e) {
                throw new GojoException("Error creating save file.");
            }
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                Task task = null;

                if (type.equals("T")) {
                    task = new Todo(description);
                } else if (type.equals("D")) {
                    task = new Deadline(description, parts[3]);
                } else if (type.equals("E")) {
                    task = new Event(description, parts[3], parts[4]);
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            throw new GojoException("Error loading save file: " + e.getMessage());
        }
        return tasks;
    }
}