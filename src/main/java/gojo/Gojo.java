package gojo;

public class Gojo {

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