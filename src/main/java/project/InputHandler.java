package project;

import java.net.InetAddress;
import java.nio.file.*;

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
        if (started) {
            field.getItems().add("Receiver already started");
            return;
        }
        (new Thread(new Receiver(12080))).start();
    }

    public static void printInput(String message) {
        field.getItems().add("You: " + message);
    }

    public static void sendMessage(String message) {
        try {
            sender.sendMessage(username + ": " + message, address, 12080);
        } catch (Exception e) {
            field.getItems().add("*** Error sending message");
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

    public static boolean fileExists(String token) {
        Path path = Paths.get(token);
        return Files.exists(path);
    }

    public static void save(String token) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveFile(token, field.getItems());
    }

    public static void load(String token) {
        FileHandler fileHandler = new FileHandler();
        try {
            field.getItems().setAll(fileHandler.loadFile(token));
        } catch (Exception e) {
            field.getItems().add("*** Error loading file");
        }
    }

    public static String getAddress() {
        return address.toString();
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
        if (command.length() >= 5 && command.substring(0,5).equals(":save")) {
            String token = command.substring(6);
            if (fileExists(token)) {
                field.getItems().add("*** File already exists");
                return;
            }
            save(command.substring(6));
            return;
        }
        if (command.length() >= 5 && command.substring(0, 5).equals(":load")) {
            String token = command.substring(6);
            if (!fileExists(token)) {
                field.getItems().add("*** File does not exist");
                return;
            }
            load(token);
            return;
        }
        if (command.length() >= 5 && command.subSequence(0, 5).equals(":help")) {
            field.getItems().add(":clear - clear chat");
            field.getItems().add(":start - start receiver");
            field.getItems().add(":connect [IP] - connect to IP");
            field.getItems().add(":config -username [username] - set username");
            field.getItems().add(":save [filename] - save chat to file");
            field.getItems().add(":load [filename] - load chat from file");
            return;
        }
        field.getItems().add("*** Unknown command: " + command);
    }
}
