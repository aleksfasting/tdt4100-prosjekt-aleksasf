package project;

import java.net.InetAddress;
import java.nio.file.*;
import java.util.ArrayList;

import javafx.scene.control.ListView;


public class InputHandler{
    private static Sender sender = new Sender(12180);
    private static InetAddress address;
    private static ArrayList<String> field;
    private static String username = "anon";
    private static boolean started = false;
    private static ListView<String> view;
    static {
        try {
            address = InetAddress.getByName("192.168.0.255");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void start() {
        if (started) {
            field.add("*** Receiver already started");
            return;
        }
        started = true;
        (new Thread(new Receiver(12080))).start();
    }

    public static void printInput(String message) {
        field.add("You: " + message);
    }

    public static void sendMessage(String message) {
        try {
            sender.sendMessage(username + ": " + message, address, 12080);
        } catch (Exception e) {
            field.add("*** Error sending message");
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

    public static ArrayList<String> getField() {
        return field;
    }

    public static void setField(ArrayList<String> newField) {
        field = newField;
    }

    public static void setView(ListView<String> v) {
        view = v;
    }

    public static ListView<String> getView() {
        return view;
    }

    public static void post(String message) {
        field.add(message);
        view.getItems().setAll(field);
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
        Path path = Paths.get("savefiles/" + token);
        return Files.exists(path);
    }

    public static void save(String token) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveFile("savefiles/" + token, field);
    }

    public static void load(String token) {
        FileHandler fileHandler = new FileHandler();
        try {
            field.clear();
            field.addAll(fileHandler.loadFile("savefiles/" + token));
        } catch (Exception e) {
            field.add("*** Error loading file");
        }
    }

    public static String getAddress() {
        return address.toString();
    }

    public static String getLastMessage() {
        return field.get(field.size() - 1);
    }



    public static ArrayList<String> handleInput(String input) {
        if (input.charAt(0) == ':') {
            handleCommand(input);
        } else {
            printInput(input);
            sendMessage(input);
        }
        return field;
    }

    public static void handleCommand(String command) {
        if (command.length() >= 6 && command.subSequence(0, 6).equals(":clear")) {
            field.clear();
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
                field.add("*** File already exists");
                return;
            }
            save(command.substring(6));
            return;
        }
        if (command.length() >= 5 && command.substring(0, 5).equals(":load")) {
            String token = command.substring(6);
            if (!fileExists(token)) {
                field.add("*** File does not exist");
                return;
            }
            load(token);
            return;
        }
        if (command.length() >= 5 && command.subSequence(0, 5).equals(":help")) {
            field.add("    :clear - clear chat");
            field.add("    :start - start receiver");
            field.add("    :connect [IP] - connect to IP");
            field.add("    :config -username [username] - set username");
            field.add("    :save [filename] - save chat to file");
            field.add("    :load [filename] - load chat from file");
            return;
        }
        field.add("*** Unknown command: " + command);
    }
}
