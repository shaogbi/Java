package network.webserver.servers;

import java.io.*;
import java.net.*;
import network.webserver.utils.*;

public class SingleThreadServer {
  private int port = 9000;
  private boolean shutdown = false;
  public SingleThreadServer() {}
  public SingleThreadServer(int port) {
    this.port = port;
  }
  public void shutdownServer() {
    shutdown = true;
  }
  public void await() {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.out.printf("Server started, listening port %d...\n", port);
    while(!shutdown) {
      Socket socket = null;
      InputStream input = null;
      OutputStream output = null;
      try {
        socket = serverSocket.accept();
        long t1 = System.currentTimeMillis();
        input = socket.getInputStream();
        output = socket.getOutputStream();
        Request request = new Request(input);
        System.out.printf("Get request: \"%s\", processed via socket %d.\n", request.getParsedUri(), socket.hashCode());
        Response response = new Response(request, output);
        response.sendStaticResource();
        input.close();
        output.close();
        socket.close();
        System.out.println("Total " + (System.currentTimeMillis() - t1));
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public static void main(String[] args) {
    SingleThreadServer server = new SingleThreadServer();
    server.await();
  }
}
