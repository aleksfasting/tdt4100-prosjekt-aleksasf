package project;

import java.net.InetAddress;

import javafx.scene.control.ListView;

public class InputHandler{
    private static Sender sender = new Sender(12180);
    private static InetAddress address;
    private static ListView<String> field;
    private static String username = "anon";
    private static boolean started = false;
    static {
        try {
            address = InetAddress.getByName("192.168.0.255");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void start() {
        if (started) return;
        (new Thread(new Receiver(12080))).start();
    }

    public static void printInput(String message) {
        field.getItems().add("You: " + message);
    }

    public static void sendMessage(String message) {
        try {
            sender.sendMessage(username + ": " + message, address, 12080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connect(String command) {
        String IP = command.substring(9);
        try {
            address = InetAddress.getByName(IP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ListView<String> getField() {
        return field;
    }

    public static void setField(ListView<String> newField) {
        field = newField;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    public static void config(String command) {
        if (command.substring(0, 9).equals("-username")) {
            setUsername(command.substring(9));
            return;
        }
    }



    public static void handleInput(String input) {
        if (input.charAt(0) == ':') {
            handleCommand(input);
        } else {
            printInput(input);
            sendMessage(input);
        }
    }

    public static void handleCommand(String command) {
        if (command.length() >= 6 && command.subSequence(0, 6).equals(":clear")) {
            field.getItems().clear();
            return;
        }
        if (command.length() >= 6 && command.substring(0, 6).equals(":start")) {
            start();
            return;
        }
        if (command.length() >= 8 && command.substring(0, 8).equals(":connect")) {
            connect(command);
            return;
        }
        if (command.length() >= 7 && command.substring(0, 7).equals(":config")) {
            config(command.substring(8));
            return;
        }
        field.getItems().add("Unknown command: " + command);
    }
}
