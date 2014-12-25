package network.multicast;

import java.net.*;

public class CLient {
  public static void main(String[] args) throws Exception {
    InetAddress group = InetAddress.getByName("228.5.6.7");
    MulticastSocket multicastSocket = new MulticastSocket();
    multicastSocket.joinGroup(group);
    byte[] buf = new byte[1024];
    DatagramPacket p = new DatagramPacket(buf, buf.length);
    multicastSocket.receive(p);
    String msg = new String(p.getData(), 0, p.getLength());
    System.out.printf("Get message from %s: %s\n", p.getAddress(), msg);
    multicastSocket.leaveGroup(group);
    multicastSocket.close();
  }
}
