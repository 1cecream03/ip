import java.util.Scanner;

public class Gojo {

    public static void main(String[] args) {
        String name = "Gojo";
        Scanner in = new Scanner(System.in);

        String[] tasks = new String[100];
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
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                printLine();
            }
            // Case 3: Add a new task (Default)
            else {
                tasks[taskCount] = command;
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
}