package main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
        int bufferLen = 16384;
        byte[] buffer = new byte[bufferLen];
        ICommunication stub = null;
        try {
            Registry registry = LocateRegistry.getRegistry("172.16.10.134", 1099);

            stub = (ICommunication) registry.lookup("ICommunication");
        } catch (Exception e) {
            System.exit(0);
        }
        try {
            InetAddress inetAddress = InetAddress.getByName("172.16.10.134");
            DatagramSocket datagramSocket = new DatagramSocket(5036, inetAddress);
            DatagramPacket packet = new DatagramPacket(buffer, bufferLen);
            while (true) {

                datagramSocket.receive(packet);
                System.out.println("Received");
                stub.setMessage(packet.getData());
            }
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
