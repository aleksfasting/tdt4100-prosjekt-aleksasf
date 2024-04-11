package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javafx.application.Platform;

public class Receiver implements Runnable{
    private DatagramSocket socket;

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
            String finalMessage = message.trim();

            Platform.runLater(() -> {
                InputHandler.post(finalMessage);
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
            InputHandler.getField().add("Error receiving message");
        }
        return null;
    }
}
