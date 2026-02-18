package gojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    //Save
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

    // Converts Task to String
    private String taskToSaveString(Task task) {
        String type = "";
        String status = task.isDone ? "1" : "0";
        String description = task.description;
        String extra = "";

        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            extra = " | " + ((Deadline) task).by;
        } else if (task instanceof Event) {
            type = "E";
            extra = " | " + ((Event) task).from + " | " + ((Event) task).to;
        }

        return type + " | " + status + " | " + description + extra;
    }

    //Load
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
                    if (isDone) task.markAsDone();
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            throw new GojoException("Error loading save file: " + e.getMessage());
        }
        return tasks;
    }
}