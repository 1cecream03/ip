import java.util.Scanner;

public class Gojo {
    // UI Configuration Constants
    private static final String DIVIDER = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String NAME = "Gojo";

    // Task Management State
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    // Command Parsing Offsets
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        formatResponse("Throughout heaven and earth, I alone am the honored one.\n" +
                "I'm " + NAME + ". Ask me anything.");

        while (true) {
            String input = in.nextLine();

            //Exit
            if (input.equals("bye")) {
                formatResponse("Bye. Don't get weak while I'm gone.");
                break;
            }
            //List all tasks
            else if (input.equals("list")) {
                String listContent = "";
                for (int i = 0; i < taskCount; i++) {
                    listContent += (i + 1) + ". " + tasks[i] + "\n";
                }
                formatResponse(listContent);
            }
            //Mark task
            else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(MARK_OFFSET));
                int index = taskNumber - 1;
                tasks[index].markAsDone();
                formatResponse("Good. I've marked this task as done:\n  " + tasks[index]);
            }
            //Unmark task
            else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(UNMARK_OFFSET));
                int index = taskNumber - 1;
                tasks[index].markAsUndone();
                formatResponse("Okay, I've marked this task as not done:\n  " + tasks[index]);
            }
            //Todo logic
            else if (input.startsWith("todo ")) {
                tasks[taskCount] = new Todo(input.substring(TODO_OFFSET));
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            //Deadline logic
            else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(DEADLINE_OFFSET).split(" /by ");
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            //Event logic
            else if (input.startsWith("event ")) {
                String[] parts = input.substring(EVENT_OFFSET).split(" /from | /to ");
                tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            //Add task
            else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                formatResponse("added: " + input);
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

    public static void printAddedMessage(Task task, int totalTasks) {
        String message = "Got it. I've added this task:\n" +
                "  " + task + "\n" +
                "Now you have " + totalTasks + " tasks in the list.";
        formatResponse(message);
    }
}