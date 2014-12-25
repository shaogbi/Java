package network.multicast;

import java.net.*;

public class Server {
  public static void main(String[] args) throws Exception {
    InetAddress group = InetAddress.getByName("228.5.6.7");
    // MulticastSocket is son class of DatagramSocket
    MulticastSocket multicastSocket = new MulticastSocket();
    multicastSocket.joinGroup(group);
    String msg = "Timestamp[" + System.currentTimeMillis() + "]";
    DatagramPacket p = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
    System.out.printf("Sending message: %s\n", msg);
    multicastSocket.send(p);
    multicastSocket.leaveGroup(group);
    multicastSocket.close();
  }
}
