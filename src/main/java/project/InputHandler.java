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
            sendMessage(input);
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

    public static void receiveMessage(ListView<String> field) {
        String message = receiver.receiveMessage();
        field.getItems().add("Received: " + message);
    }

    public static void connect(String command) {
        String IP = command.substring(9);
        try {
            address = InetAddress.getByName(IP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleCommand(ListView<String> field, String command) {
        if (command.equals(":clear")) {
            field.getItems().clear();
            return;
        }
        if (command.equals(":receive")) {
            receiveMessage(field);
        }
        if (command.contains(":connect")) {
            connect(command);
            return;
        }
        field.getItems().add("Unknown command: " + command);
    }
}
