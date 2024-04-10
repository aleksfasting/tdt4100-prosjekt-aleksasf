package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;

public class Receiver implements Runnable{
    private DatagramSocket socket;
    private List<String> messages = new ArrayList<>();

    public Receiver(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String message = receiveMessage();
            if (message == null) {
                continue;
            }
            message = message.trim();
            if (!message.isEmpty()) {
                messages.add(message);
            }

            Platform.runLater(() -> {
                for (String message1 : messages) {
                    System.out.println("Received: " + message1);
                }
                messages.clear();
            });
        }
    }

    public String receiveMessage() {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
