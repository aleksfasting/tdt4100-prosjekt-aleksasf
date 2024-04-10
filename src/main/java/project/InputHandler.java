package project;

import java.net.InetAddress;

import javafx.scene.control.ListView;

public class InputHandler{
    private static Sender sender = new Sender(12180);
    private static Receiver receiver = new Receiver(12080);
    private static InetAddress address;
    static {
        try {
            address = InetAddress.getByName("192.168.0.106");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static void sendMessage(String message) {
        try {
            sender.sendMessage(message, address, 12080);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (command.equals(":send")) {
            sendMessage("Hello, World!");
            return;
        }
        if (command.equals(":receive")) {
            String message = receiver.receiveMessage();
            field.getItems().add("Received: " + message);
            return;
        }
        if (command.equals(":connect")) {
            String IP = command.substring(9);
            try {
                address = InetAddress.getByName(IP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        field.getItems().add("Unknown command: " + command);
    }
}
