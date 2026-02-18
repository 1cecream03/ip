package gojo;

import java.util.ArrayList;
import java.util.Scanner;

public class Gojo {
    // UI Configuration Constants
    private static final String DIVIDER = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String NAME = "Gojo";

    // gojo.Task Management State
    private static ArrayList<Task> tasks = new ArrayList<>();

    //Storage
    private static Storage storage;

    // Command Parsing Offsets
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int DELETE_OFFSET = 7;


    public static void main(String[] args) {
        storage = new Storage("data/gojo.txt");

        try {
            // 2. Load tasks (Level 7 logic adapted for ArrayList)
            tasks = storage.load();
        } catch (GojoException e) {
            formatResponse("Error loading tasks: " + e.getMessage());
            tasks = new ArrayList<>(); // Fallback to empty list
        }

        Scanner in = new Scanner(System.in);

        formatResponse("Throughout heaven and earth, I alone am the honored one.\n" +
                "I'm " + NAME + ". Ask me anything.");

        while (true) {
            String input = in.nextLine();
            try {
                //Exit
                if (input.equals("bye")) {
                    formatResponse("Bye. Don't get weak while I'm gone.");
                    break;
                }
                //List all tasks
                else if (input.equals("list")) {
                    String listContent = "";
                    for (int i = 0; i < tasks.size(); i++) {
                        listContent += (i + 1) + ". " + tasks.get(i) + "\n";
                    }
                    formatResponse(listContent);
                }
                //Mark task
                else if (input.startsWith("mark ")) {
                    if (input.trim().equals("mark")) {
                        throw new GojoException("You didn't tell me which one to mark.");
                    }
                    int taskNumber = Integer.parseInt(input.substring(MARK_OFFSET));
                    int index = taskNumber - 1;
                    tasks.get(index).markAsDone();
                    storage.save(tasks);
                    formatResponse("Good. I've marked this task as done:\n  " + tasks.get(index));
                }
                //Unmark task
                else if (input.startsWith("unmark ")) {
                    if (input.trim().equals("unmark")) {
                        throw new GojoException("You didn't tell me which one to unmark.");
                    }
                    int taskNumber = Integer.parseInt(input.substring(UNMARK_OFFSET));
                    int index = taskNumber - 1;
                    tasks.get(index).markAsUndone();
                    storage.save(tasks);
                    formatResponse("Okay, I've marked this task as not done:\n  " + tasks.get(index));
                }
                //Todo logic
                else if (input.startsWith("todo ")) {
                    if (input.trim().length() <= 4) {
                        throw new GojoException("You want me to do nothing? The description of a todo cannot be empty.");
                    }
                    Task newTask = new Todo(input.substring(TODO_OFFSET));
                    tasks.add(newTask);
                    storage.save(tasks);
                    printAddedMessage(newTask);
                }
                //Deadline logic
                else if (input.startsWith("deadline ")) {
                    if (input.trim().length() <= 8) {
                        throw new GojoException("A deadline cannot be empty.");
                    }
                    //Split the input string to separate the task description from the deadline time.
                    String[] parts = input.substring(DEADLINE_OFFSET).split(" /by ");
                    if (parts.length < 2) {
                        throw new GojoException("Use '/by' to tell me the deadline. Don't make me guess.");
                    }
                    Task newTask = new Deadline(parts[0], parts[1]);
                    tasks.add(newTask);
                    storage.save(tasks);
                    printAddedMessage(newTask);
                }
                //Event logic
                else if (input.startsWith("event ")) {
                    if (input.trim().length() <= 5) {
                        throw new GojoException("An event cannot be empty.");
                    }
                    String[] parts = input.substring(EVENT_OFFSET).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new GojoException("Incomplete event. Use '/from' and '/to'.");
                    }
                    Task newTask = new Event(parts[0], parts[1], parts[2]);
                    tasks.add(newTask);
                    storage.save(tasks);
                    printAddedMessage(newTask);
                }
                //Delete logic
                else if (input.startsWith("delete ")) {
                    if (input.trim().equals("delete")) {
                        throw new GojoException("You didn't tell me which one to delete.");
                    }
                    try {
                        int taskNumber = Integer.parseInt(input.substring(DELETE_OFFSET).trim());
                        int index = taskNumber - 1;

                        if (index >= 0 && index < tasks.size()) { //making sure it is a valid no.
                            Task removedTask = tasks.remove(index);
                            storage.save(tasks);
                            formatResponse("Noted. I've removed this task:\n" +
                                    "  " + removedTask + "\n" +
                                    "Now you have " + tasks.size() + " tasks in the list.");
                        } else {
                            throw new GojoException("That task number doesn't exist. Can't delete void.");
                        }
                    } catch (NumberFormatException e) {
                        throw new GojoException("That's not a valid number.");
                    }
                }
                //Unknown Command
                else {
                    throw new GojoException("What is that? I don't know what that means.");
                }

            } catch (GojoException e) {
                formatResponse("Umm... " + e.getMessage());
            } catch (NumberFormatException e) {
                // This catches cases where they type "mark xyz"
                formatResponse("OPPS!!! That's not a valid number.");
            }
        }
    }

    public static void printLine() {
        System.out.println(INDENT + DIVIDER);
    }

    public static void formatResponse(String message) {
        printLine();
        // Splits the message into individual lines based on newline characters (\n)
        // and iterates through each line one by one
        for (String line : message.split("\n")) {
            System.out.println(INDENT + " " + line);
        }
        printLine();
    }

    public static void printAddedMessage(Task task) {
        String message = "Got it. I've added this task:\n" +
                "  " + task + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.";
        formatResponse(message);
    }
}