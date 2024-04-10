package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
            message = message.trim();
            if (!message.isEmpty()) {
                InputHandler.getField().getItems().add("Received: " + message);
                InputHandler.getField().scrollTo(InputHandler.getField().getItems().size() - 1);
            }
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
