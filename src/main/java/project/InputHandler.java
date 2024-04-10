package project;

import java.net.InetAddress;

import javafx.scene.control.ListView;

public class InputHandler {
    private static Sender sender = new Sender(2080);

    public static void handleInput(ListView<String> field, String input) {
        if (input.charAt(0) == ':') {
            handleCommand(field, input);
        } else {
            printInput(field, input);
        }
    }

    public static void printInput(ListView<String> field, String message) {
        field.getItems().add("You: " + message);
    }

    public static void handleCommand(ListView<String> field, String command) {
        if (command.equals(":help")) {
            field.getItems().add("    Commands:");
            field.getItems().add("    :help - display this message");
            field.getItems().add("    :clear - clear the screen");
            return;
        }
        if (command.equals(":clear")) {
            field.getItems().clear();
            return;
        }
        if (command == ":send") {  
            try {
                String message = "Hello";
                InetAddress address = InetAddress.getByName("192.168.0.106");
                sender.sendMessage(message, address, 2080);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (command == ":receive") {
            Receiver receiver = new Receiver(2080);
            receiver.receiveMessage();
            return;
        }
        field.getItems().add("Unknown command: " + command);
    }
}
