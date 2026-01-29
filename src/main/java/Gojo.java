import java.util.Scanner;

public class Gojo {

    public static void main(String[] args) {
        String name = "Gojo";
        Scanner in = new Scanner(System.in);

        printLine();
        System.out.println("Throughout heaven and earth, I alone am the honored one.");
        System.out.println("I'm " + name + ". Ask me anything.");
        printLine();

        String command;
        while (true) {
            command = in.nextLine();
            if (command.equals("bye")) {
                break;
            }

            printLine();
            System.out.println(command);
            printLine();
        }

        System.out.println("See ya!");
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }
}