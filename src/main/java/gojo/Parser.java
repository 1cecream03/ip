package gojo;

import java.util.Scanner;

public class Parser {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int DELETE_OFFSET = 7;
    private static final int FIND_OFFSET = 5;

    public Parser(TaskList tasks, Ui ui, Storage storage) {
        this.tasks = tasks;
        this.ui = ui;
        this.storage = storage;
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        ui.printWelcome();

        while (true) {
            String input = in.nextLine();
            try {
                //Exit
                if (input.equals("bye")) {
                    ui.printBye();
                    break;
                }
                //List all tasks
                else if (input.equals("list")) {
                    ui.formatResponse(tasks.listTasks());
                }
                //Mark task
                else if (input.startsWith("mark ")) {
                    if (input.trim().equals("mark")) {
                        throw new GojoException("You didn't tell me which one to mark.");
                    }
                    int index = Integer.parseInt(input.substring(MARK_OFFSET)) - 1;
                    tasks.getTask(index).markAsDone();
                    storage.save(tasks.getTasks());
                    ui.formatResponse("Good. I've marked this task as done:\n  " + tasks.getTask(index));
                }
                //Unmark task
                else if (input.startsWith("unmark ")) {
                    if (input.trim().equals("unmark")) {
                        throw new GojoException("You didn't tell me which one to unmark.");
                    }
                    int index = Integer.parseInt(input.substring(UNMARK_OFFSET)) - 1;
                    tasks.getTask(index).markAsUndone();
                    storage.save(tasks.getTasks());
                    ui.formatResponse("Okay, I've marked this task as not done:\n  " + tasks.getTask(index));
                }
                //Todo logic
                else if (input.startsWith("todo ")) {
                    if (input.trim().length() <= 4) {
                        throw new GojoException("You want me to do nothing? The description of a todo cannot be empty.");
                    }
                    Task newTask = new Todo(input.substring(TODO_OFFSET));
                    tasks.addTask(newTask);
                    storage.save(tasks.getTasks());
                    ui.printAddedMessage(newTask, tasks.size());
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
                    tasks.addTask(newTask);
                    storage.save(tasks.getTasks());
                    ui.printAddedMessage(newTask, tasks.size());
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
                    tasks.addTask(newTask);
                    storage.save(tasks.getTasks());
                    ui.printAddedMessage(newTask, tasks.size());
                }
                //Delete logic
                else if (input.startsWith("delete ")) {
                    if (input.trim().equals("delete")) {
                        throw new GojoException("You didn't tell me which one to delete.");
                    }
                    try {
                        int index = Integer.parseInt(input.substring(DELETE_OFFSET).trim()) - 1;
                        if (index >= 0 && index < tasks.size()) {
                            Task removedTask = tasks.deleteTask(index);
                            storage.save(tasks.getTasks());
                            ui.formatResponse("Noted. I've removed this task:\n" +
                                    "  " + removedTask + "\n" +
                                    "Now you have " + tasks.size() + " tasks in the list.");
                        } else {
                            throw new GojoException("That task number doesn't exist. Can't delete void.");
                        }
                    } catch (NumberFormatException e) {
                        throw new GojoException("That's not a valid number.");
                    }
                }
                //Find logic
                else if (input.startsWith("find ")) {
                    if (input.trim().length() <= 5) {
                        throw new GojoException("You want me to find nothing? Give me a keyword.");
                    }
                    String keyword = input.substring(FIND_OFFSET).trim();
                    ui.formatResponse(tasks.findTasks(keyword));
                }
                //Unknown Command
                else {
                    throw new GojoException("What is that? I don't know what that means.");
                }
            } catch (GojoException e) {
                ui.printError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.formatResponse("OPPS!!! That's not a valid number.");
            }
        }
    }
}