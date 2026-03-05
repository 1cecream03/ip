package gojo;

/**
 * Main entry point for the Gojo chatbot application.
 * Initialises the necessary components and starts the application.
 */
public class Gojo {

    /**
     * Main method that starts the Gojo chatbot.
     * Loads tasks from storage, initialises the UI and parser, and begins the command loop.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/gojo.txt");
        TaskList tasks;

        try {
            tasks = new TaskList(storage.load());
        } catch (GojoException e) {
            ui.printError("Error loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }

        Parser parser = new Parser(tasks, ui, storage);
        parser.run();
    }
}