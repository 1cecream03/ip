import java.util.Scanner;

public class Gojo {

    public static void main(String[] args) {
        String name = "Gojo";
        Scanner in = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        printLine();
        System.out.println("Throughout heaven and earth, I alone am the honored one.");
        System.out.println("I'm " + name + ". Ask me anything.");
        printLine();

        String command;
        while (true) {
            command = in.nextLine();

            // Case 1: Exit
            if (command.equals("bye")) {
                printLine();
                System.out.println("Bye. Don't get weak while I'm gone.");
                printLine();
                break;
            }
            // Case 2: List all tasks
            else if (command.equals("list")) {
                printLine();
                System.out.println("Here are the tasks in your list. Try to keep up:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                printLine();
            }
            // Case 3: Add a new task (Default)
            else if (command.startsWith("mark ")) {
                // Extract the number (e.g., "2") and convert to integer
                int taskNumber = Integer.parseInt(command.substring(5));
                int index = taskNumber - 1;

                tasks[index].markAsDone();

                printLine();
                System.out.println("Good. I've marked this task as done:");
                System.out.println("  " + tasks[index]);
                printLine();
            }
            else if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                int index = taskNumber - 1;

                tasks[index].markAsUndone();

                printLine();
                System.out.println("Okay, I've marked this task as not done:");
                System.out.println("  " + tasks[index]);
                printLine();
            }
            else if (command.startsWith("todo ")) {
                tasks[taskCount] = new Todo(command.substring(5));
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            else if (command.startsWith("deadline ")) {
                String[] parts = command.substring(9).split(" /by ");
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            else if (command.startsWith("event ")) {
                String[] parts = command.substring(6).split(" /from | /to ");
                tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            else {
                tasks[taskCount] = new Task(command);
                taskCount++;

                printLine();
                System.out.println("added: " + command);
                printLine();
            }
        }
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printAddedMessage(Task task, int totalTasks) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printLine();
    }
}