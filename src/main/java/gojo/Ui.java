package gojo;

/**
 * Handles all user interface interactions for the Gojo chatbot.
 * Responsible for displaying messages, responses, and errors to the user.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String INDENT = "    ";

    /**
     * Prints a divider line to the console.
     */
    public void printLine() {
        System.out.println(INDENT + DIVIDER);
    }

    /**
     * Formats and prints a response message wrapped between two divider lines.
     *
     * @param message The message to display.
     */
    public void formatResponse(String message) {
        printLine();
        for (String line : message.split("\n")) {
            System.out.println(INDENT + " " + line);
        }
        printLine();
    }

    /**
     * Displays a confirmation message after a task has been added.
     *
     * @param task The task that was added.
     * @param size The current number of tasks in the list.
     */
    public void printAddedMessage(Task task, int size) {
        String message = "Got it. I've added this task:\n" +
                "  " + task + "\n" +
                "Now you have " + size + " tasks in the list.";
        formatResponse(message);
    }

    /**
     * Displays the welcome message when the chatbot starts.
     */
    public void printWelcome() {
        formatResponse("Throughout heaven and earth, I alone am the honored one.\n" +
                "I'm Gojo. Ask me anything.");
    }

    /**
     * Displays the goodbye message when the user exits.
     */
    public void printBye() {
        formatResponse("Bye. Don't get weak while I'm gone.");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void printError(String message) {
        formatResponse("Umm... " + message);
    }
}