package project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.ObservableList;
import java.io.File;
import java.util.ArrayList;

public class FileHandler {
    public void saveFile(String fileName, ObservableList<String> messages) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for (String message : messages) {
                writer.println(message);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            InputHandler.getField().getItems().add("Error saving file");
        }
    }

    public ArrayList<String> loadFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        ArrayList<String> messages = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            messages.add(message);
        }
        scanner.close();
        return messages;
    }
}
