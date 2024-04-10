package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver {
    private DatagramSocket socket;

    public Receiver(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
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
