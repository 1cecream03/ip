package gojo;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String INDENT = "    ";

    public void printLine() {
        System.out.println(INDENT + DIVIDER);
    }

    public void formatResponse(String message) {
        printLine();
        for (String line : message.split("\n")) {
            System.out.println(INDENT + " " + line);
        }
        printLine();
    }

    public void printAddedMessage(Task task, int size) {
        String message = "Got it. I've added this task:\n" +
                "  " + task + "\n" +
                "Now you have " + size + " tasks in the list.";
        formatResponse(message);
    }

    public void printWelcome() {
        formatResponse("Throughout heaven and earth, I alone am the honored one.\n" +
                "I'm Gojo. Ask me anything.");
    }

    public void printBye() {
        formatResponse("Bye. Don't get weak while I'm gone.");
    }

    public void printError(String message) {
        formatResponse("Umm... " + message);
    }
}